package net.frozenorb.potpvp.party.command;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.party.Party;
import net.frozenorb.potpvp.party.PartyHandler;
import net.frozenorb.potpvp.party.PartyInvite;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PartyJoinCommand {

    // default value for password parameter used to detect that password
    // wasn't provided. No Optional<String> :(
    private static final String NO_PASSWORD_PROVIDED = "skasjkdasdjhksahjd";

    @Command(names = {"party join", "p join", "t join", "team join", "join", "f join"}, permission = "")
    public static void partyJoin(Player sender, @Param(name = "player") Player target, @Param(name = "password", defaultValue = NO_PASSWORD_PROVIDED) String providedPassword) {
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        Party targetParty = partyHandler.getParty(target);

        if (partyHandler.hasParty(sender)) {
            sender.sendMessage(ChatColor.RED + "You are already in a party. You must leave your current party first.");
            return;
        }

        if (targetParty == null) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is not in a party.");
            return;
        }

        switch (targetParty.getAccessRestriction()) {
            case PUBLIC:
                targetParty.join(sender);
                break;
            case INVITE_ONLY:
                PartyInvite invite = targetParty.getInvite(sender.getUniqueId());

                if (invite != null) {
                    targetParty.join(sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have an invitation to this party.");
                }

                break;
            case PASSWORD:
                String correctPassword = targetParty.getPassword();

                if (correctPassword.equals(providedPassword)) {
                    targetParty.join(sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid password.");
                }

                break;
            default:
                break;
        }
    }

}