package com;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		AssetPool.load();
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Team Project");
		Project project = new Project();

		window.add(project);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
