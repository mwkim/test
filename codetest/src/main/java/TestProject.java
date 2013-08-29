

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectComponent;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.selectors.FilenameSelector;

public class TestProject extends ProjectComponent{
	
	
	
	
	public void execute(){
		/////////////////////////
		
		Project project = new Project();
		project.setName("test");
		project.setDefault("compile");
		project.setBaseDir(new File("."));
		
		project.setProperty("app.name", "");
		project.setProperty("app.path", "/" + project.getProperty("app.name"));
		project.setProperty("app.version", "");
		project.setProperty("build.home", project.getBaseDir() + "");
		project.setProperty("build.new", "");
		project.setProperty("catalina.home", "./");
		project.setProperty("src.home", project.getBaseDir() + "/src");
		project.setProperty("web.home", project.getBaseDir() + "/web");
		project.setProperty("webconfig.home", project.getProperty("web.home") + "/WEB-INF");
		project.setProperty("serverlib.home", project.getBaseDir() + "/serverlib");
		project.setProperty("applib.home", project.getProperty("webconfig.home") + "/lib");
		project.setProperty("resources.home", project.getProperty("webconfig.home") + "/classes/messageResources");
		project.setProperty("js.home", project.getProperty("web.home") + "/js");
		
		/////////////////////////
		
		project.setProperty("complie.debug", "true");
		project.setProperty("compile.deprecation", "false");
		project.setProperty("compile.optimize", "true");
		
		
		/****
		 * 
		 */
		FilenameSelector filenameSelector = new FilenameSelector();
		filenameSelector.setName("*.jar");
		
	 	Path path = new Path(project);
	 	
	 	FileSet fileSet1 = new FileSet();
	 	fileSet1.setDir(new File(project.getProperty("serverlib.home") + "/was"));
	 	fileSet1.addFilename(filenameSelector);
	 	path.addFileset(fileSet1);
	 	
	 	FileSet fileSet2 = new FileSet();
	 	fileSet2.setDir(new File(project.getProperty("serverlib.home") + "/etc"));
	 	fileSet2.addFilename(filenameSelector);
	 	path.addFileset(fileSet2);
	 	
	 	FileSet fileSet3 = new FileSet();
	 	fileSet3.setDir(new File(project.getProperty("applib.home")));
	 	fileSet3.addFilename(filenameSelector);
	 	path.addFileset(fileSet3);
	 	
	 	project.addIdReference("compile.classpath", path);
	 	
	 	///////////////////////////
	 	
	 	
	 	Target targetAll = new Target();
	 	targetAll.setName("all");
	 	targetAll.setDepends("clean,compile");
	 	project.addTarget(targetAll);
	 	
	 	//////////////////
	 	
	 	Target targetClean = new Target();
	 	targetClean.setName("clean");
	 	Task taskClean = new Task() { 		
	 		@Override
	 		public void execute() throws BuildException {
	 			File file1 = new File(getProject().getProperty("build.home"));
	 			file1.delete();
	 			
	 			File file2 = new File(getProject().getProperty("dist.home"));
	 			file2.delete();
	 			super.execute();
	 		}

		}; 
	 	targetClean.addTask(taskClean);
	 	project.addTarget(targetClean);
	 	
	 	//////////////////
	 	
	 	
	 	Target targetCompile = new Target();
	 	targetCompile.setName("compile");
	 	//targetCompile.setDepends("prepare");
	 	Task taskCompile = new Task() {
	 		@Override
	 		public void execute() throws BuildException {
	 			
	 			//System.out.println(getProject().getProperty("build.home"));
	 			
	 			//File f = new File(getProject().getProperty("build.home"));
	 			//if(!f.mkdirs()){
	 				//ERROR
	 			//}
	 			
	 			try {
					Process oProcess = new ProcessBuilder("cmd", "/c", "javac").start();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 			
	 			super.execute();
	 		}
	 	};
	 	targetCompile.addTask(taskCompile);
	 	targetCompile.setProject(project);
	 	project.addTarget(targetCompile);
	 	taskCompile.execute();
	 	

	 	Hashtable<Object, Object> hashtable =  project.getTargets();
	 	System.out.println(hashtable);
	 	
	 	//project.executeTarget("compile");
	 	//project.fireBuildStarted();
	 	
	 	
	 		 	
	 	
	 	
		//project.addReference(referenceName, value);
		
		//CallTargetWithClasspath classpath = new CallTargetWithClasspath();
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestProject testProject = new TestProject();
		testProject.execute();
	}

}
