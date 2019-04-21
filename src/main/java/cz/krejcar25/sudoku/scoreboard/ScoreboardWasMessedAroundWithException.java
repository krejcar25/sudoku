package cz.krejcar25.sudoku.scoreboard;

// I've been thinking about this for like 10 minutes and couldn't find a better name for it...
// Change it if you want but pleeease don't put too much attention to it. This little puppy gets job done.
public class ScoreboardWasMessedAroundWithException extends Exception {
	public ScoreboardWasMessedAroundWithException() {
		super("The scoreboard file was messed around with. Someone tried to edit the data but forgot to check the SHA256 checksum which didn't match up. Generating fresh scoreboard and showing a humiliating message to the user now!");
	}
}
