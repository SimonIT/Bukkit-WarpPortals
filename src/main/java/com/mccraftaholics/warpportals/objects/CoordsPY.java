package com.mccraftaholics.warpportals.objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class CoordsPY {
	public double x, y, z;
	public float pitch, yaw;
	public World world;

	public String getWorldName() {
		if (world != null)
			return world.getName();
		return null;
	}

	public CoordsPY(World world, double i, double j, double k, float pitch, float yaw) {
		this.world = world;
		x = i;
		y = j;
		z = k;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	public CoordsPY(Location loc) {
		this(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
	}

	public CoordsPY(Coords crd) {
		this(crd.world, crd.x, crd.y, crd.z, 0, 0);
	}

	public CoordsPY(String coordsString) throws Exception {
		String t = coordsString.trim();
		if (t.matches("\\(.+,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*\\)")) {
			String n = coordsString.substring(1, coordsString.length() - 1);
			String[] s = n.split(",");

			world = Bukkit.getWorld(s[0]);
			if (world == null)
				throw NullWorldException.createForWorldName(s[0]);

			x = Double.parseDouble(s[1]);
			y = Double.parseDouble(s[2]);
			z = Double.parseDouble(s[3]);
			pitch = Float.parseFloat(s[4]);
			yaw = Float.parseFloat(s[5]);
		} else {
			throw new Exception("Invalid Coordinate String");
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(getWorldName()).append(x).append(y).append(z).append(pitch).append(yaw).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof CoordsPY))
			return false;
		else {
			CoordsPY crd = (CoordsPY) obj;
			return crd.getWorldName().equals(this.getWorldName()) && crd.x == this.x && crd.y == this.y && crd.z == this.z && crd.pitch == this.pitch
					&& crd.yaw == this.yaw;
		}
	}

	public String toString() {
		return "(" + getWorldName() + "," + x + "," + y + "," + z + "," + pitch + ","
				+ yaw + ")";
	}

	public String toNiceString() {
		return "(" + getWorldName() + ", " + Math.floor(x) + ", " + Math.floor(y) + ", " + Math.floor(z) + ")";
	}

	public CoordsPY clone() {
		return new CoordsPY(this.world, this.x, this.y, this.z, this.pitch, this.yaw);
	}

}
