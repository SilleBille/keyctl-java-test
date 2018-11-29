public class KeyctlClearTest {
	public static void main(String arg[]) {

		try {

			ProcessBuilder pb = new ProcessBuilder();
			pb.command("/bin/bash", "-c", "keyctl clear @u");
			
			System.out.println("Clearing out user's keyring");

			Process p = pb.start();
			p.destroy();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
