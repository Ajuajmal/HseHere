package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import java.util.ArrayList;

public final class LeaderboardRef extends zzc implements Leaderboard {
    private final int zzaDQ;
    private final Game zzaJc;

    LeaderboardRef(DataHolder holder, int dataRow, int numChildren) {
        super(holder, dataRow);
        this.zzaDQ = numChildren;
        this.zzaJc = new GameRef(holder, dataRow);
    }

    public boolean equals(Object obj) {
        return LeaderboardEntity.zza(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return zzxI();
    }

    public String getDisplayName() {
        return getString("name");
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        zza("name", dataOut);
    }

    public Game getGame() {
        return this.zzaJc;
    }

    public Uri getIconImageUri() {
        return zzcA("board_icon_image_uri");
    }

    public String getIconImageUrl() {
        return getString("board_icon_image_url");
    }

    public String getLeaderboardId() {
        return getString("external_leaderboard_id");
    }

    public int getScoreOrder() {
        return getInteger("score_order");
    }

    public ArrayList<LeaderboardVariant> getVariants() {
        ArrayList<LeaderboardVariant> arrayList = new ArrayList(this.zzaDQ);
        for (int i = 0; i < this.zzaDQ; i++) {
            arrayList.add(new LeaderboardVariantRef(this.zzahi, this.zzaje + i));
        }
        return arrayList;
    }

    public int hashCode() {
        return LeaderboardEntity.zza(this);
    }

    public String toString() {
        return LeaderboardEntity.zzb(this);
    }

    public Leaderboard zzxI() {
        return new LeaderboardEntity(this);
    }
}
