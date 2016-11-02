package net.frozenorb.potpvp.party.command;

import com.google.common.collect.ImmutableSet;

import net.frozenorb.potpvp.PotPvPLang;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.kittype.menu.SelectKitTypeMenu;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.potpvp.match.MatchTeam;
import net.frozenorb.potpvp.party.Party;
import net.frozenorb.potpvp.party.PartyHandler;
import net.frozenorb.potpvp.validation.PotPvPValidation;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PartyFfaCommand {

    @Command(names = {"party ffa", "p ffa", "t ffa", "team ffa", "f ffa"}, permission = "")
    public static void partyFfa(Player sender) {
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        Party party = partyHandler.getParty(sender);

        if (party == null) {
            sender.sendMessage(PotPvPLang.NOT_IN_PARTY);
        } else if (!party.isLeader(sender.getUniqueId())) {
            sender.sendMessage(PotPvPLang.NOT_LEADER_OF_PARTY);
        } else  {
            MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();

            if (!PotPvPValidation.canStartFfa(party, sender)) {
                return;
            }

            new SelectKitTypeMenu(kitType -> {
                sender.closeInventory();

                if (!PotPvPValidation.canStartFfa(party, sender)) {
                    return;
                }

                List<MatchTeam> teams = new ArrayList<>();

                for (UUID member : party.getMembers()) {
                    String uuid = UUID.randomUUID().toString();
                    teams.add(new MatchTeam(uuid, ImmutableSet.of(member)));
                }

                matchHandler.startMatch(teams, kitType);
            }).openMenu(sender);
        }
    }

    @Command(names = {"party devffa", "p devffa", "t devffa", "team devffa", "f devffa"}, permission = "")
    public static void partyDevFfa(Player sender, @Param(name = "team size", defaultValue = "1") int teamSize) {
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        Party party = partyHandler.getParty(sender);

        if (party == null) {
            sender.sendMessage(PotPvPLang.NOT_IN_PARTY);
        } else if (!party.isLeader(sender.getUniqueId())) {
            sender.sendMessage(PotPvPLang.NOT_LEADER_OF_PARTY);
        } else  {
            MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();

            if (!PotPvPValidation.canStartFfa(party, sender)) {
                return;
            }

            new SelectKitTypeMenu(kitType -> {
                sender.closeInventory();

                if (!PotPvPValidation.canStartFfa(party, sender)) {
                    return;
                }

                List<MatchTeam> teams = new ArrayList<>();
                List<UUID> availableMembers = new ArrayList<>(party.getMembers());

                while (availableMembers.size() >= teamSize) {
                    List<UUID> teamMembers = new ArrayList<>();

                    for (int i = 0; i < teamSize; i++) {
                        teamMembers.add(availableMembers.remove(0));
                    }

                    String uuid = UUID.randomUUID().toString();
                    teams.add(new MatchTeam(uuid, ImmutableSet.copyOf(teamMembers)));
                }

                matchHandler.startMatch(teams, kitType);
            }).openMenu(sender);
        }
    }

}