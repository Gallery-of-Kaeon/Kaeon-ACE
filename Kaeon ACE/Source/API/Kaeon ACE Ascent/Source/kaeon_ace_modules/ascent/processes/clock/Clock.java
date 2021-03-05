package kaeon_ace_modules.ascent.processes.clock;

import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Clock extends PhilosophersStone {

	private long previousFrameTimeMillis;
	private long previousTickTimeMillis;

	private double frameCount;
	private double tickCount;
	
	public Clock() {
		PhilosophersStoneUtilities.tag(this, "Clock");
	}

	public void incrementFrameCount() {

		long currentTimeMillis = System.currentTimeMillis();

		if(currentTimeMillis - (currentTimeMillis % 1000) >
			previousFrameTimeMillis - (previousFrameTimeMillis % 1000)) {
			
			frameCount = 0;
		}

		frameCount++;

		previousFrameTimeMillis = currentTimeMillis;
	}

	public void incrementTickCount() {

		long currentTimeMillis = System.currentTimeMillis();

		if(currentTimeMillis - (currentTimeMillis % 1000) >
			previousTickTimeMillis - (previousTickTimeMillis % 1000)) {
			
			tickCount = 0;
		}

		tickCount++;

		previousTickTimeMillis = currentTimeMillis;
	}

	public double getFramesPerSecond() {

		long currentTimeMillis = System.currentTimeMillis();
		
		if(frameCount > 0 && currentTimeMillis % 1000 > 0)
			return frameCount * (1000.0 / (currentTimeMillis % 1000));
		
		else
			return -1;
	}

	public double getTicksPerSecond() {

		long currentTimeMillis = System.currentTimeMillis();
		
		if(tickCount > 0 && currentTimeMillis % 1000 > 0)
			return tickCount * (1000.0 / (currentTimeMillis % 1000));
		
		else
			return -1;
	}
}