package com.extendedclip.papi.expansion.towny;

import com.palmergames.bukkit.TownyChat.Chat;
import com.palmergames.bukkit.TownyChat.TownyChatFormatter;
import com.palmergames.bukkit.TownyChat.channels.Channel;
import com.palmergames.bukkit.TownyChat.channels.channelTypes;
import com.palmergames.bukkit.TownyChat.config.ChatSettings;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TownyChatHook {

  private Chat chat;
  private Towny towny;
  private boolean enabled;

  public TownyChatHook(Towny towny) {
    if (towny != null) {
      this.towny = towny;
      chat = (Chat) Bukkit.getPluginManager().getPlugin("TownyChat");
    }
    enabled = chat != null;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Object replace(Player p, String args) {
    try {
      Resident r = TownyUniverse.getDataSource().getResident(p.getName());
      switch (args) {
        case "world":
          return String.format(ChatSettings.getWorldTag(), p.getWorld().getName());
        case "town":
          return r.hasTown() ? r.getTown().getName() : "";
        case "townformatted":
          return TownyChatFormatter.formatTownTag(r, false, true);
        case "towntag":
          return TownyChatFormatter.formatTownTag(r, false, false);
        case "towntagoverride":
          return TownyChatFormatter.formatTownTag(r, true, false);
        case "nation":
          return r.hasNation() ? r.getTown().getNation().getName() : "";
        case "nationformatted":
          return TownyChatFormatter.formatNationTag(r, false, true);
        case "nationtag":
          return TownyChatFormatter.formatNationTag(r, false, false);
        case "nationtagoverride":
          return TownyChatFormatter.formatNationTag(r, true, false);
        case "townytag":
          return TownyChatFormatter.formatTownyTag(r, false, false);
        case "townyformatted":
          return TownyChatFormatter.formatTownyTag(r, false, true);
        case "townytagoverride":
          return TownyChatFormatter.formatTownyTag(r, true, false);
        case "title":
          return r.hasTitle() ? r.getTitle() : "";
        case "surname":
          return r.hasSurname() ? r.getSurname() : "";
        case "townynameprefix":
          return TownyFormatter.getNamePrefix(r);
        case "townynamepostfix":
          return TownyFormatter.getNamePostfix(r);
        case "townyprefix":
          return r.hasTitle() ? r.getTitle() : TownyFormatter.getNamePrefix(r);
        case "townypostfix":
          return r.hasSurname() ? r.getSurname() : TownyFormatter.getNamePostfix(r);
        case "townycolor":
          return r.isMayor() ? ChatSettings.getMayorColour()
              : r.isKing() ? ChatSettings.getKingColour() : ChatSettings.getResidentColour();
        case "group":
          return TownyUniverse.getPermissionSource().getPlayerGroup(p);
        case "permprefix":
          return TownyUniverse.getPermissionSource().getPrefixSuffix(r, "prefix");
        case "permsuffix":
          return TownyUniverse.getPermissionSource().getPrefixSuffix(r, "suffix");
        case "channeltag":
          return TownyChatFormatter.formatTownyTag(r, false, false);
      }
      Channel activeChannel = null;
      if (args.contains(":")) {
        String[] parts = args.split(":");
        args = parts[0];
        activeChannel = chat.getChannelsHandler()
            .getActiveChannel(p, channelTypes.valueOf(parts[1]));
      } else {
        for (Channel c : chat.getChannelsHandler().getAllChannels().values()) {
          if (towny.hasPlayerMode(p, c.getName())) {
            activeChannel = c;
            break;
          }
        }
      }
      if (activeChannel == null) {
        return "";
      }
      switch (args) {
        case "channel_tag":
          return activeChannel.getChannelTag();
        case "channel_name":
          return activeChannel.getName();
        case "message_color":
          return activeChannel.getMessageColour();
      }
    } catch (Exception e) {
      return "";
    }
    return null;
  }
}
