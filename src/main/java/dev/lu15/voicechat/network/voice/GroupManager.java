package dev.lu15.voicechat.network.voice;

import dev.lu15.voicechat.network.minecraft.Group;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GroupManager {

    private final @NotNull Map<UUID, Group> groups = new HashMap<>();
    private final @NotNull Map<Player, UUID> playerGroups = new HashMap<>();
    private final @NotNull Map<UUID, ArrayList<Player>> groupPlayers = new HashMap<>();
    private final @NotNull Map<UUID, String> groupPassword = new HashMap<>();

    @Nullable
    public Group getGroup(Player player) {
        UUID groupId = this.playerGroups.get(player);
        return groupId != null ? this.groups.get(groupId) : null;
    }

    @Nullable
    public Group getGroup(UUID group) {
        return this.groups.get(group);
    }

    public Collection<Group> getGroups() {
        return Collections.unmodifiableCollection(this.groups.values());
    }

    @Nullable
    public List<Player> getPlayers(Group group) {
        return getPlayers(group.id());
    }

    @Nullable
    public List<Player> getPlayers(UUID group) {
        return this.groupPlayers.get(group);
    }

    public boolean hasGroup(UUID group) {
        return this.groups.containsKey(group);
    }

    public boolean hasGroup(Group group) {
        return this.groups.containsKey(group.id());
    }

    @Nullable
    public String getPassword(Group group) {
        return this.groupPassword.get(group.id());
    }

    @Nullable
    public String getPassword(UUID group) {
        return this.groupPassword.get(group);
    }

    public void createGroup(Group group, @Nullable String password) {
        this.groupPassword.put(group.id(), password);
        this.groups.put(group.id(), group);
        this.groupPlayers.putIfAbsent(group.id(), new ArrayList<>());
    }

    public void setGroup(Player player, Group group) {
        setGroup(player, group.id());
    }

    public void setGroup(Player player, UUID group) {
        leaveGroup(player);

        ArrayList<Player> players = this.groupPlayers.computeIfAbsent(group, k -> new ArrayList<>());
        if (!players.contains(player)) {
            players.add(player);
        }
        this.playerGroups.put(player, group);
    }

    public void leaveGroup(Player player) {
        UUID groupId = this.playerGroups.remove(player);
        if (groupId != null) {
            ArrayList<Player> playersInGroup = this.groupPlayers.get(groupId);
            if (playersInGroup != null) {
                playersInGroup.remove(player);
            }
        }
    }

    public void removeGroup(Group group) {
        removeGroup(group.id());
    }

    public void removeGroup(UUID groupId) {
        if (!this.groups.containsKey(groupId)) {
            return;
        }

        List<Player> players = this.groupPlayers.get(groupId);
        if (players != null) {
            for (Player player : new ArrayList<>(players)) {
                if (Objects.equals(this.playerGroups.get(player), groupId)) {
                    this.playerGroups.remove(player);
                }
            }
        }

        this.groupPlayers.remove(groupId);
        this.groups.remove(groupId);
        this.groupPassword.remove(groupId);
    }
}
