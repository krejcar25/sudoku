package cz.krejcar25.sudoku;

import java.util.Date;

public class Timer {

	private final String name;
	private long startTime = 0;
	private long stopTime = 0;
	private long pauseStart = -1;
	private long pauseTotal = 0;
	private boolean running = false;
	private boolean finished = false;

	public Timer(String name) {
		this.name = name;
	}

	public static String formatMillis(long val) {
		StringBuilder builder = new StringBuilder(20);
		String sgn = "";

		if (val < 0) {
			sgn = "-";
			val = Math.abs(val);
		}

		builder.append(sgn);
		builder.append(val / 3600000);
		val %= 3600000;
		builder.append(":");
		builder.append(val / 60000);
		val %= 60000;
		builder.append(":");
		builder.append(val / 1000);
		val %= 1000;
		builder.append(".");
		builder.append(val);
		return builder.toString();
	}

	public void start() {
		start(0);
	}

	private void start(long offset) {
		if (finished) return;
		long startTime = System.currentTimeMillis() - offset;
		if (running && pauseStart > -1) {
			pauseTotal += (startTime - pauseStart);
			pauseStart = -1;
			System.out.println("Timer " + name + " resumed at " + startTime);
		}
		else if (!running) {
			this.startTime = startTime;
			System.out.println("Timer " + name + " started at " + startTime);
			this.running = true;
		}
	}

	public void pause() {
		pause(0);
	}

	private void pause(long offset) {
		if (running && pauseStart == -1) {
			long pauseStart = System.currentTimeMillis() - offset;
			this.pauseStart = pauseStart;
			System.out.println("Timer " + name + " paused at " + pauseStart);
		}
	}

	public void stop() {
		if (!running) return;
		this.stopTime = System.currentTimeMillis();
		System.out.println("Timer " + name + " stopped at " + stopTime);
		this.running = false;
		this.finished = true;
	}

	//elaspsed time in milliseconds
	public long getElapsedTime() {
		long elapsed = -pauseTotal;
		if (isPaused()) elapsed -= System.currentTimeMillis() - pauseStart;
		if (running) {
			elapsed += (System.currentTimeMillis() - startTime);
		}
		else {
			elapsed += (stopTime - startTime);
		}
		return elapsed;
	}

	//elaspsed time in seconds
	public long getElapsedTimeSecs() {
		return getElapsedTime() / 1000;
	}

	public boolean isPaused() {
		return pauseStart > -1;
	}

	public boolean isRunning() {
		return running;
	}

	public long getMinutes() {
		return getElapsedTimeSecs() / 60;
	}

	public long getSeconds() {
		return getElapsedTime() % 60;
	}

	public Date getStartDate() {
		return new Date(startTime);
	}
}
