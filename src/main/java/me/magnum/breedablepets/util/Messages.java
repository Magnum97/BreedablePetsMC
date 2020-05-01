package me.magnum.breedablepets.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;

public class Messages {
	public static void sendBar(Player pl, String title) {
		try {
			pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Common.colorize(title)));

		} catch (final Throwable t) {
			Common.tell(pl, title);
		}
	}
}
