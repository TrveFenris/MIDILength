package midi.process;

import midi.parameters.MIDILengthParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class MidiProcessor {
	
	private static double runMIDILenghtScript(List<String> args) {
		ArrayList<String> commandAndArgs = new ArrayList<String>();

		commandAndArgs.add(MIDILengthParameters.SCRIPT_COMMAND.getValue());
		commandAndArgs.add(MIDILengthParameters.SCRIPT_NAME.getValue());
		commandAndArgs.addAll(args);

		BufferedReader reader = null;
		try {
			Process p = new ProcessBuilder(commandAndArgs).start();
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			return Double.parseDouble(reader.readLine());
		}
		catch (IOException e) {
			System.out.println("Error ocurred while trying to execute command (IO Exception). Returning negative value.");
			e.printStackTrace();
		}
		catch(NumberFormatException nfe) {
			System.out.println("Error ocurred while trying to execute command (Bad number format). Returning negative value.");
		}
		catch(NullPointerException npe) {
			System.out.println("Error ocurred while trying to execute command (Null argument or result). Returning negative value.");
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.out.println("Error ocurred while trying to close the buffered reader...");
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static double getMIDILength(String path) {
		ArrayList<String> args = new ArrayList<String>();
		args.add(path);
		return runMIDILenghtScript(args);
	}
}
