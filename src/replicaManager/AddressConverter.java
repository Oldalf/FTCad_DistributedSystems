package replicaManager;

import org.jgroups.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddressConverter {

	private long mostSignificantBits;
	private long leastSignificantBits;

	@JsonIgnore
	private UUID convertedUUID;

	public AddressConverter(long mostSignificantBits, long leastSignificantBits) {
		this.mostSignificantBits = mostSignificantBits;
		this.leastSignificantBits = leastSignificantBits;
	}

	/*
	 * For jackson
	 */
	public AddressConverter() {
	}
	
	public UUID PossiblyCreateAndGetJGroupsUUID() {
		if (convertedUUID == null)
			convertedUUID = new UUID(mostSignificantBits, leastSignificantBits);
		return convertedUUID;
	}

	public long getMostSignificantBits() {
		return mostSignificantBits;
	}

	public void setMostSignificantBits(long mostSignificantBits) {
		this.mostSignificantBits = mostSignificantBits;
	}

	public long getLeastSignificantBits() {
		return leastSignificantBits;
	}

	public void setLeastSignificantBits(long leastSignificantBits) {
		this.leastSignificantBits = leastSignificantBits;
	}
}
