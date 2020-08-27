package com.cepheid.cloud.skel.model;

import java.util.stream.Stream;

public enum State {
	
	Undefined("UNDEFINED"),Valid("VALID"),Invalid("INVALID");
	
	private String state;

	State(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public static State of(String state) {
        return Stream.of(State.values())
          .filter(p -> p.getState().equals(state))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }	

}
