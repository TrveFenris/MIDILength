package midi.parameters;

public enum MIDILengthParameters {
	PERL_RELATIVE_DIRECTORY("perl/bin/"),
	SCRIPT_RELATIVE_DIR("perl/script/"),
	SCRIPT_COMMAND(PERL_RELATIVE_DIRECTORY.getValue() + "perl.exe"),
	SCRIPT_NAME(SCRIPT_RELATIVE_DIR.getValue() + "midi-playlength.pl"),
	;
	
	private String value;
	
	private MIDILengthParameters(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
