

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class Test extends Task{
	
	String _message;
	public void setMessage(String message){
		_message = message;
	}
	
	public void execute() {
		setMessage("TEST!!!!!!!!!!!!!");
		if(_message == null){
			throw new BuildException("No message set.");
		}
		log(_message);
	}	
	
	
	
	public static void main(String[] args) {
		Test test = new Test();
		test.execute();
	}
}
