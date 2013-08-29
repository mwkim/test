

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.CallTarget;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

public class CallTargetWithClasspath extends CallTarget {
	private Path classpath; 

    private Reference classpathRef; 

    @Override
    public void execute() throws BuildException {
        Path resolvedClassPath = null;
        if (classpathRef != null) {
            resolvedClassPath = (Path)classpathRef.getReferencedObject();
        } else {
            resolvedClassPath = classpath;
        }
        AntClassLoader acl = new AntClassLoader(getProject(), resolvedClassPath);
        acl.setThreadContextLoader();
        super.execute();
        acl.resetThreadContextLoader();
    } 

 

    public Path getClasspath() { return classpath; } 

    public void setClasspath(Path targetClasspath) { this.classpath = targetClasspath; } 

    public Path createClasspath() {
        classpath = new Path(getProject());
        return classpath;
    } 

    public Reference getClasspathRef() { return classpathRef; } 

    public void setClasspathRef(Reference classpathRef) { this.classpathRef = classpathRef; } 
}
