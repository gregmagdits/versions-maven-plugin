package org.codehaus.mojo.versions;

import javax.xml.stream.XMLStreamException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.mojo.versions.api.PomHelper;
import org.codehaus.mojo.versions.rewriting.ModifiedPomXMLEventReader;

@Mojo(name = "bulk-update-regex-properties", requiresProject = true, requiresDirectInvocation = true, threadSafe = true)
public class NimhdaBulkRegexPropertyUpdateMojo extends AbstractVersionsUpdaterMojo {

    /**
     * The pre-transformation value.
     */
    @Parameter(property = "from", required = true)
    private String from;

    /**
     * The replacement.
     */
    @Parameter(property = "to", required = true)
    private String to;

    /**
     * @param pom
     *            the pom to update.
     * @throws MojoExecutionException
     *             when things go wrong
     * @throws MojoFailureException
     *             when things go wrong in a very bad way
     * @throws XMLStreamException
     *             when things go wrong with XML streaming
     * @see AbstractVersionsUpdaterMojo#update(ModifiedPomXMLEventReader)
     */
    protected void update(ModifiedPomXMLEventReader pom) throws MojoExecutionException, MojoFailureException, XMLStreamException {

        for (Object key : getProject().getProperties().keySet()) {

            if (getProject().getProperties().get(key).toString().contains(from)) {
                getLog().info(String.format("%s=%s", key.toString(), getProject().getProperties().get(key).toString().replaceAll(from, to)));
                PomHelper.setPropertyVersion(pom, null, key.toString(), getProject().getProperties().get(key).toString().replaceAll(from, to));
            }
        }
    }

}
