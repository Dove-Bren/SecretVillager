package com.SkyIsland.SecretVillager;

public enum VillagerType {
	INVINCIBLE(0),
	TRADE_SWAPPED(1),
	BOTH(2);
	
	private int index;
	
	private VillagerType(int i) {
		this.index = i;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public static VillagerType fromIndex(int index) {
		for (VillagerType v : values()) {
			if (v.index == index) {
				return v;
			}
		}
		return null;
	}
}
