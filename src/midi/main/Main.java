package midi.main;

import midi.process.MidiProcessor;

public class Main {

	public static void main(String[] args) {
		if(args.length == 2) {
			if(!args[1].isEmpty()) {
				double t = MidiProcessor.getMIDILength(args[1]);
				int minutes = (int) (t/60), seconds = (int) (t - minutes * 60);
				if((t - minutes * 60) % 1 > 0.5) {
					seconds++;
				}
				System.out.println(minutes + ":" + seconds);
			}
			else {
				System.out.println("Error: Argument was empty.");
			}
		}
		else { 
			System.out.println("Error: Expected one argument.");
		}
	}

}