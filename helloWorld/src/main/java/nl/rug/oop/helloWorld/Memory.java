package nl.rug.oop.helloWorld;

import java.util.HashSet;

public class Memory {
	private HashSet<String> memory;

	public Memory() {
		memory = new HashSet<>();
	}

	public void remember(String said) {
		if (!memory.contains(said))
			memory.add(said);
	}

	public boolean canRemember(String said) {
		return memory.contains(said);
	}
}