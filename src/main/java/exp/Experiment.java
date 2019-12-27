package exp;

import core.World;
import java.util.ArrayList;
import java.util.List;
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
/**
 * Class for holding information related to a single experiment in a distributed multi-experiment scenario. The class is annotated to make it possible to map a CSV record directly into an Experiment instance.
 * @author Pedro Victori
 */

public class Experiment{
	private String settingsFile;
	private int iterations;
	private int stepLimit;

	List<World> worlds = new ArrayList<>();

	public Experiment(String settingsFile, int iterations, int stepLimit) {
		this.settingsFile = settingsFile;
		this.iterations = iterations;
		this.stepLimit = stepLimit;

		//create a World instance for each repetition
		for (int i = 0; i < iterations; i++) {
			World world = new World(stepLimit);
			worlds.add(world);
			world.whenFinished(() -> {
				worlds.remove(world);
				//todo integrate statistics
			}); //add a listener for when the simulation reaches the step limit
		}
	}

	public void start() {
		for (World world : worlds) {
			world.start();
		}
	}
}
