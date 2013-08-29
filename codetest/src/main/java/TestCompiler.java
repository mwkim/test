import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class TestCompiler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 파일 읽어올 폴더 경로
		File dir = new File("d:/package");

		if (dir.exists() == false) {
			System.out.println("경로가 존재 하지 않습니다.");
			return;
		}

		// 탐색한 파일을 저장할 리스트
		ArrayList<File> files = new ArrayList<File>();
		// 탐색한 파일명을 저장하는 리스트
		ArrayList<String> fileList = new ArrayList<String>();

		// 파일명index
		int fileIndex = 0;

		// 파일 탐색
		visitAllFiles(files, dir);

		// 탐색한 파일명을 리스트에 담음
		for (File f : files) {
			fileList.add(fileIndex, f.getAbsolutePath());
			fileIndex++;
		}

		// 자바 컴파일러 선언
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(fileList);

		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

		// 컴파일러 실행 리턴 (true/false)
		Boolean success = task.call();

		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
			// 컴파일 에러시 리포트 출력
			System.out.println("error-report");
			System.out.println("Code->" + diagnostic.getCode());
			System.out.println("Column Number->" + diagnostic.getColumnNumber());
			System.out.println("End Position->" + diagnostic.getEndPosition());
			System.out.println("Kind->" + diagnostic.getKind());
			System.out.println("Line Number->" + diagnostic.getLineNumber());
			System.out.println("Message->" + diagnostic.getMessage(Locale.ENGLISH));
			System.out.println("Position->" + diagnostic.getPosition());
			System.out.println("Source" + diagnostic.getSource());
			System.out.println("Start Position->" + diagnostic.getStartPosition());
			System.out.println("\n");

		}

		try {
			fileManager.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Success : " + success);
	}

	public static void visitAllFiles(ArrayList files, File dir) {

		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (File f : children) {
				// 재귀 호출 사용
				// 하위 폴더 탐색 부분
				visitAllFiles(files, f);
			}
		} else {
			files.add(dir);
		}
	}

}
