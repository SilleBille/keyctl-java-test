import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class KeyctlJavaTest {
	public static void main(String arg[]) {

		try {

			ProcessBuilder pb = new ProcessBuilder();
			pb.command("/bin/bash", "-c", "keyctl search @u user nuxwdog:user");

			Process p = pb.start();
			String keyID = output(p.getInputStream());
			p.destroy();

			System.out.println("Initial Test Key ID: " + keyID);

			try {
				Integer.parseInt(keyID);
				System.out.println("Already key available. Don't prompt user");
				return;
			} catch (NumberFormatException e) {
				// Key isn't available. Let it execute.
			}

			// ============= Prompt user for password =======//
			pb.command("/bin/bash", "-c", "systemd-ask-password internaldb:");
			p = pb.start();
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
