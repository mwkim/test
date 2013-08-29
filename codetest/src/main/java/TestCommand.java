import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestCommand implements Runnable {
	private Thread t = null;
	private InputStream is;

	public TestCommand(InputStream is) {
		this.is = is;
		t = new Thread(this);
		t.start();
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	public static void main(String args[]) throws Exception {
		String s;

		try {
			/*
			 * 자바 1.4 이하에서는 이렇게 
			 * Runtime oRuntime = Runtime.getRuntime(); 
			 * Process oProcess = oRuntime.exec("cmd /c dir /?");
			 */

			// 자바 1.5 이상에서는 이렇게 1줄로
			// Process oProcess = new ProcessBuilder("cmd", "/c", "dir", "/?").start();
			// Process oProcess = new
			// ProcessBuilder("d:\\ffmpeg.exe","-i","d:\\aaa.mp3","-ab","128k","-y","-f","mp3","d:\\ttt.mp3").start();
			Process oProcess = new ProcessBuilder("cmd", "/c", "dir", "C:\\").start();

			new TestCommand(oProcess.getInputStream());
			new TestCommand(oProcess.getErrorStream());
			oProcess.waitFor();
			System.out.println("Done.");
			/*
			 * // 외부 프로그램 출력 읽기 
			 * BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream())); 
			 * BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));
			 */
			
			
			/*
			 * // "표준 출력"과 "표준 에러 출력"을 출력 
			 * while ((s = stdOut.readLine()) != null) 
			 * System.out.println(s); 
			 * 
			 * while ((s = stdError.readLine()) != null) 
			 * System.err.println(s);
			 */
			
			BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
			while ((s = stdOut.readLine()) != null) { 
				System.out.println(s);
				System.out.println("11111111111");	
			}
			
			// 외부 프로그램 반환값 출력 (이 부분은 필수가 아님)
			System.out.println("Exit Code: " + oProcess.exitValue());
			System.exit(oProcess.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의
			// 반환값으로 삼기

		} catch (IOException e) { // 에러 처리
			System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
			System.exit(-1);
		}
		
	}
	
	/*
		src.substring(0,src.length()-1)
		
		
		
		String dest="";
		    	System.out.println(src);
		    	System.out.println(dest);
		    	System.out.println("Convert 192k into 128k....");
		    	try {
		    		Process oProcess = new ProcessBuilder("HoTrans.exe",src,dest).start();
		    		new FileDown(oProcess.getInputStream());
				   new FileDown(oProcess.getErrorStream());
				   oProcess.waitFor();
				   System.out.println("Done.");
				   System.exit(oProcess.exitValue()); // 외부 프로그램의 반환값을, 이 자바 프로그램 자체의
				   
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 */
}
