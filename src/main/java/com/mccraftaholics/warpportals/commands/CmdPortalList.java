package com.mccraftaholics.warpportals.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.mccraftaholics.warpportals.bukkit.CommandHandler;
import com.mccraftaholics.warpportals.objects.PortalInfo;

public class CmdPortalList extends CommandHandlerObject {

	private static final String[] ALIASES = { "wp-portal-list", "wppl", "plist" };
	private static final String PERMISSION = "warpportals.admin.destination.list";
	private static final boolean REQUIRES_PLAYER = false;

	@Override
	public String getPermission() {
		return PERMISSION;
	}

	@Override
	public String[] getAliases() {
		return ALIASES;
	}

	@Override
	public boolean doesRequirePlayer() {
		return REQUIRES_PLAYER;
	}

	@Override
	boolean command(CommandSender sender, String[] args, CommandHandler main) {
		StringBuilder sblist = new StringBuilder();
		sblist.append("Portals:");
		for (String portalName : main.mPortalManager.getPortalNames()) {
			// Retrieve Portal Info
			PortalInfo portalInfo = main.mPortalManager.getPortalInfo(portalName);
			// Retrieve linked Teleport Destination
			String destText = main.mPortalManager.getDestinationName(portalInfo.tpCoords);
			/*
			 * If the teleport destination does not have a name, show the
			 * coordinates
			 */
			if (destText == null)
				destText = portalInfo.tpCoords.toNiceString();
			try {
				sblist.append(ChatColor.WHITE)
						.append("\n - ")
						.append(ChatColor.RED)
						.append(portalName)
						.append(ChatColor.YELLOW)
						.append(" (")
						.append(portalInfo.blockCoordArray.get(0).world.getName())
						.append(") ")
						.append(ChatColor.WHITE)
						.append("-> ")
						.append(ChatColor.AQUA)
						.append(destText);
			} catch (Exception e) {
				// Catches exceptions when blockCoordArray is 0 in length
			}
		}
		sender.sendMessage(main.mCC + sblist.toString());
		return true;
	}
}
