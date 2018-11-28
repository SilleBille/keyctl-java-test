import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class KeyctlJavaTest {
	public static void main(String arg[]) {
		try {
			ProcessBuilder pb = new ProcessBuilder();//  padd user nuxwdog:internalDB mkdtest1 @u"});
			pb.command("/bin/bash", "-c", "systemd-ask-password internaldb:");
			//pb.command("/bin/echo", "Hello", " world...");
			Process p = pb.start();
			String password = output(p.getInputStream());
			System.out.println("Output: " + password);
			p.destroy();
			
			
			// ================== Try to add key to user's keyring ====================== //
			
			pb.command("/bin/bash", "-c", "keyctl add user nuxwdog:user " + password + " @u");
			p = pb.start();
			
			System.out.println("Output from original KeyID: " + output(p.getInputStream()));
			p.destroy();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String output(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} finally {
			br.close();
		}
		return sb.toString().trim();
	}

}


