/*
 *
 * Towny-Expansion
 * Copyright (C) 2018-2019 Ryan McCarthy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package com.extendedclip.papi.expansion.towny;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import java.util.List;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TownyExpansion extends PlaceholderExpansion {

  private final String NAME = "Towny";
  private final String IDENTIFIER = NAME.toLowerCase();
  private final String VERSION = getClass().getPackage().getImplementationVersion();

  private TownyChatHook chatHook = null;

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getIdentifier() {
    return IDENTIFIER;
  }

  // overriding this will check if the plugin is available automatically
  @Override
  public String getRequiredPlugin() { return NAME; }

  @Override
  public String getAuthor() {
    return "clip";
  }

  @Override
  public String getVersion() {
    return VERSION;
  }

  @Override
  public boolean register() {
    if (Bukkit.getPluginManager().getPlugin("TownyChat") != null) {
      chatHook = new TownyChatHook((Towny) Bukkit.getPluginManager().getPlugin(NAME));
      if (chatHook.isEnabled()) {
        getPlaceholderAPI().getLogger().info("Hooked into TownyChat for %towny_chat_<arg>% placeholders");
      } else {
        chatHook = null;
      }
    }
    return super.register();
  }

  @Override
  public String onPlaceholderRequest(Player p, String identifier) {

    if (p == null) {
      return "";
    }

    switch (identifier) {
      case "town":
        return getPlayersTown(p);
      case "friends":
        return getPlayersFriends(p);
      case "nation":
        return getPlayersNation(p);
      case "title":
        return getPlayersTownyTitle(p);
      case "town_residents":
        return getTownResidents(p);
      case "town_size":
        return getTownSize(p);
      case "town_tag":
        return getTownTag(p);
      case "town_balance":
        return getTownBankBalance(p);
      case "town_mayor":
        return getTownMayor(p);
      case "surname":
        return getPlayersSurname(p);
      case "town_rank":
        return getTownRank(p);
      case "nation_rank":
        return getNationRank(p);
      case "nation_balance":
        return getNationBankBalance(p);
    }

    if (identifier.startsWith("chat_")) {
      return chatHook == null ? null : chatHook.replace(p, identifier.replace("chat_", "")).toString();
    }
    return null;
  }

  private String getPlayersTown(Player p) {
    try {
      return TownyUniverse.getDataSource().getResident(p.getName()).getTown().getName();
    } catch (Exception e) {
    }
    return "";
  }

  private String getPlayersFriends(Player p) {
    try {
      return String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getFriends().size());
    } catch (Exception e) {
    }
    return "";
  }

  private String getPlayersNation(Player p) {
    try {
      return TownyUniverse.getDataSource().getResident(p.getName()).getTown().getNation().getName();
    } catch (Exception e) {
    }
    return "";
  }

  private String getPlayersTownyTitle(Player p) {
    try {
      return TownyUniverse.getDataSource().getResident(p.getName()).getTitle();
    } catch (Exception e) {
    }
    return "";
  }

  private String getPlayersSurname(Player p) {
    try {
      return TownyUniverse.getDataSource().getResident(p.getName()).getSurname();
    } catch (Exception e) {
    }
    return "";
  }

  private String getTownResidents(Player p) {
    try {
      return String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getNumResidents());
    } catch (Exception e) {
    }
    return "";
  }

  private String getTownBankBalance(Player p) {
    try {
      return String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getHoldingBalance());
    } catch (Exception e) {
    }
    return "";
  }

  private String getTownMayor(Player p) {
    try {
      return TownyUniverse.getDataSource().getResident(p.getName()).getTown().getMayor().getName();
    } catch (Exception e) {
    }
    return "";
  }

  private String getTownRank(Player p) {
    try {
      return TownyUniverse.getDataSource().getResident(p.getName()).getTownRanks().get(0);
    } catch (Exception e) {
    }
    return "";
  }

  private String getNationRank(Player p) {
    try {
      List<String> ranks = TownyUniverse.getDataSource().getResident(p.getName()).getNationRanks();
      return ranks == null ? "" : ranks.get(0);
    } catch (Exception e) {
    }
    return "";
  }

  private String getTownSize(Player p) {
    try {
      return String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getTotalBlocks());
    } catch (Exception e) {
    }
    return "";
  }

  private String getTownTag(Player p) {
    try {
      return TownyUniverse.getDataSource().getResident(p.getName()).getTown().getTag();
    } catch (Exception e) {
    }
    return "";
  }
  
  private String getNationBankBalance(Player p) {
    try {
      return String.valueOf(TownyUniverse.getDataSource().getResident(p.getName()).getTown().getNation().getHoldingBalance());
    } catch (Exception e) {
    }
    return "";
  }
}
