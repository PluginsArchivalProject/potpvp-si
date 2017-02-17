package net.frozenorb.potpvp.lobby.menu;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.Match;
import net.frozenorb.potpvp.match.MatchState;
import net.frozenorb.potpvp.match.MatchTeam;
import net.frozenorb.potpvp.setting.Setting;
import net.frozenorb.potpvp.setting.SettingHandler;
import net.frozenorb.qlib.menu.Button;
import net.frozenorb.qlib.menu.pagination.PaginatedMenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SpectateMenu extends PaginatedMenu {

    public SpectateMenu() {
        setAutoUpdate(true);
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Spectate a match";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        SettingHandler settingHandler = PotPvPSI.getInstance().getSettingHandler();
        Map<Integer, Button> buttons = new HashMap<>();
        int i = 0;

        for (Match match : PotPvPSI.getInstance().getMatchHandler().getHostedMatches()) {
            // players can view this menu while spectating
            if (match.isSpectator(player.getUniqueId())) {
                continue;
            }

            if (match.getTeams().size() != 2 || match.getState() == MatchState.ENDING) {
                continue;
            }

            int numTotalPlayers = 0;
            int numSpecEnabled = 0;

            for (MatchTeam team : match.getTeams()) {
                for (UUID member : team.getAliveMembers()) {
                    numTotalPlayers++;

                    if (settingHandler.getSetting(Bukkit.getPlayer(member), Setting.ALLOW_SPECTATORS)) {
                        numSpecEnabled++;
                    }
                }
            }

            // if less than 50% of participants have spectators enabled
            // we won't render this match in the menu
            if ((float) numSpecEnabled / (float) numTotalPlayers < 0.5) {
                continue;
            }

            buttons.put(i++, new SpectateButton(match));
        }

        return buttons;
    }

}