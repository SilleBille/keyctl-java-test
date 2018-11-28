import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class KeyctlRetrieve {
	public static void main(String arg[]) {
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command("/bin/bash", "-c", "keyctl search @u user nuxwdog:user");
			
			Process p = pb.start();
			String keyID = output(p.getInputStream());
			p.destroy();
			
			System.out.println("Key ID: " + keyID);
			
			pb.command("/bin/bash", "-c", "keyctl print " + keyID);
			p = pb.start();
			String keyValue = output(p.getInputStream());
			p.destroy();
			
			System.out.println("Key Value: " + keyValue);
			while(true) {}

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
