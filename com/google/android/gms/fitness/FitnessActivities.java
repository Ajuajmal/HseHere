package com.google.android.gms.fitness;

import com.google.android.gms.location.LocationRequest;
import hse.here2.bd_provider;

public class FitnessActivities {
    public static final String AEROBICS = "aerobics";
    public static final String BADMINTON = "badminton";
    public static final String BASEBALL = "baseball";
    public static final String BASKETBALL = "basketball";
    public static final String BIATHLON = "biathlon";
    public static final String BIKING = "biking";
    public static final String BIKING_HAND = "biking.hand";
    public static final String BIKING_MOUNTAIN = "biking.mountain";
    public static final String BIKING_ROAD = "biking.road";
    public static final String BIKING_SPINNING = "biking.spinning";
    public static final String BIKING_STATIONARY = "biking.stationary";
    public static final String BIKING_UTILITY = "biking.utility";
    public static final String BOXING = "boxing";
    public static final String CALISTHENICS = "calisthenics";
    public static final String CIRCUIT_TRAINING = "circuit_training";
    public static final String CRICKET = "cricket";
    public static final String CROSSFIT = "crossfit";
    public static final String CURLING = "curling";
    public static final String DANCING = "dancing";
    public static final String DIVING = "diving";
    public static final String ELEVATOR = "elevator";
    public static final String ELLIPTICAL = "elliptical";
    public static final String ERGOMETER = "ergometer";
    public static final String ESCALATOR = "escalator";
    public static final String EXTRA_STATUS = "actionStatus";
    public static final String FENCING = "fencing";
    public static final String FOOTBALL_AMERICAN = "football.american";
    public static final String FOOTBALL_AUSTRALIAN = "football.australian";
    public static final String FOOTBALL_SOCCER = "football.soccer";
    public static final String FRISBEE_DISC = "frisbee_disc";
    public static final String GARDENING = "gardening";
    public static final String GOLF = "golf";
    public static final String GYMNASTICS = "gymnastics";
    public static final String HANDBALL = "handball";
    public static final String HIGH_INTENSITY_INTERVAL_TRAINING = "interval_training.high_intensity";
    public static final String HIKING = "hiking";
    public static final String HOCKEY = "hockey";
    public static final String HORSEBACK_RIDING = "horseback_riding";
    public static final String HOUSEWORK = "housework";
    public static final String ICE_SKATING = "ice_skating";
    public static final String INTERVAL_TRAINING = "interval_training";
    public static final String IN_VEHICLE = "in_vehicle";
    public static final String JUMP_ROPE = "jump_rope";
    public static final String KAYAKING = "kayaking";
    public static final String KETTLEBELL_TRAINING = "kettlebell_training";
    public static final String KICKBOXING = "kickboxing";
    public static final String KICK_SCOOTER = "kick_scooter";
    public static final String KITESURFING = "kitesurfing";
    public static final String MARTIAL_ARTS = "martial_arts";
    public static final String MEDITATION = "meditation";
    public static final String MIME_TYPE_PREFIX = "vnd.google.fitness.activity/";
    public static final String MIXED_MARTIAL_ARTS = "martial_arts.mixed";
    public static final String ON_FOOT = "on_foot";
    public static final String OTHER = "other";
    public static final String P90X = "p90x";
    public static final String PARAGLIDING = "paragliding";
    public static final String PILATES = "pilates";
    public static final String POLO = "polo";
    public static final String RACQUETBALL = "racquetball";
    public static final String ROCK_CLIMBING = "rock_climbing";
    public static final String ROWING = "rowing";
    public static final String ROWING_MACHINE = "rowing.machine";
    public static final String RUGBY = "rugby";
    public static final String RUNNING = "running";
    public static final String RUNNING_JOGGING = "running.jogging";
    public static final String RUNNING_SAND = "running.sand";
    public static final String RUNNING_TREADMILL = "running.treadmill";
    public static final String SAILING = "sailing";
    public static final String SCUBA_DIVING = "scuba_diving";
    public static final String SKATEBOARDING = "skateboarding";
    public static final String SKATING = "skating";
    public static final String SKATING_CROSS = "skating.cross";
    public static final String SKATING_INDOOR = "skating.indoor";
    public static final String SKATING_INLINE = "skating.inline";
    public static final String SKIING = "skiing";
    public static final String SKIING_BACK_COUNTRY = "skiing.back_country";
    public static final String SKIING_CROSS_COUNTRY = "skiing.cross_country";
    public static final String SKIING_DOWNHILL = "skiing.downhill";
    public static final String SKIING_KITE = "skiing.kite";
    public static final String SKIING_ROLLER = "skiing.roller";
    public static final String SLEDDING = "sledding";
    public static final String SLEEP = "sleep";
    public static final String SLEEP_AWAKE = "sleep.awake";
    public static final String SLEEP_DEEP = "sleep.deep";
    public static final String SLEEP_LIGHT = "sleep.light";
    public static final String SLEEP_REM = "sleep.rem";
    public static final String SNOWBOARDING = "snowboarding";
    public static final String SNOWMOBILE = "snowmobile";
    public static final String SNOWSHOEING = "snowshoeing";
    public static final String SQUASH = "squash";
    public static final String STAIR_CLIMBING = "stair_climbing";
    public static final String STAIR_CLIMBING_MACHINE = "stair_climbing.machine";
    public static final String STANDUP_PADDLEBOARDING = "standup_paddleboarding";
    public static final String STATUS_ACTIVE = "ActiveActionStatus";
    public static final String STATUS_COMPLETED = "CompletedActionStatus";
    public static final String STILL = "still";
    public static final String STRENGTH_TRAINING = "strength_training";
    public static final String SURFING = "surfing";
    public static final String SWIMMING = "swimming";
    public static final String SWIMMING_OPEN_WATER = "swimming.open_water";
    public static final String SWIMMING_POOL = "swimming.pool";
    public static final String TABLE_TENNIS = "table_tennis";
    public static final String TEAM_SPORTS = "team_sports";
    public static final String TENNIS = "tennis";
    public static final String TILTING = "tilting";
    public static final String TREADMILL = "treadmill";
    public static final String UNKNOWN = "unknown";
    public static final String VOLLEYBALL = "volleyball";
    public static final String VOLLEYBALL_BEACH = "volleyball.beach";
    public static final String VOLLEYBALL_INDOOR = "volleyball.indoor";
    public static final String WAKEBOARDING = "wakeboarding";
    public static final String WALKING = "walking";
    public static final String WALKING_FITNESS = "walking.fitness";
    public static final String WALKING_NORDIC = "walking.nordic";
    public static final String WALKING_STROLLER = "walking.stroller";
    public static final String WALKING_TREADMILL = "walking.treadmill";
    public static final String WATER_POLO = "water_polo";
    public static final String WEIGHTLIFTING = "weightlifting";
    public static final String WHEELCHAIR = "wheelchair";
    public static final String WINDSURFING = "windsurfing";
    public static final String YOGA = "yoga";
    public static final String ZUMBA = "zumba";
    private static final String[] zzavS = new String[119];

    static {
        zzavS[9] = AEROBICS;
        zzavS[10] = BADMINTON;
        zzavS[11] = BASEBALL;
        zzavS[12] = BASKETBALL;
        zzavS[13] = BIATHLON;
        zzavS[1] = BIKING;
        zzavS[14] = BIKING_HAND;
        zzavS[15] = BIKING_MOUNTAIN;
        zzavS[16] = BIKING_ROAD;
        zzavS[17] = BIKING_SPINNING;
        zzavS[18] = BIKING_STATIONARY;
        zzavS[19] = BIKING_UTILITY;
        zzavS[20] = BOXING;
        zzavS[21] = CALISTHENICS;
        zzavS[22] = CIRCUIT_TRAINING;
        zzavS[23] = CRICKET;
        zzavS[bd_provider.CATS_ID] = CROSSFIT;
        zzavS[106] = CURLING;
        zzavS[24] = DANCING;
        zzavS[102] = DIVING;
        zzavS[117] = ELEVATOR;
        zzavS[25] = ELLIPTICAL;
        zzavS[103] = ERGOMETER;
        zzavS[118] = ESCALATOR;
        zzavS[6] = "exiting_vehicle";
        zzavS[26] = FENCING;
        zzavS[27] = FOOTBALL_AMERICAN;
        zzavS[28] = FOOTBALL_AUSTRALIAN;
        zzavS[29] = FOOTBALL_SOCCER;
        zzavS[30] = FRISBEE_DISC;
        zzavS[31] = GARDENING;
        zzavS[32] = GOLF;
        zzavS[33] = GYMNASTICS;
        zzavS[34] = HANDBALL;
        zzavS[114] = HIGH_INTENSITY_INTERVAL_TRAINING;
        zzavS[35] = HIKING;
        zzavS[36] = HOCKEY;
        zzavS[37] = HORSEBACK_RIDING;
        zzavS[38] = HOUSEWORK;
        zzavS[LocationRequest.PRIORITY_LOW_POWER] = ICE_SKATING;
        zzavS[0] = IN_VEHICLE;
        zzavS[115] = INTERVAL_TRAINING;
        zzavS[39] = JUMP_ROPE;
        zzavS[40] = KAYAKING;
        zzavS[41] = KETTLEBELL_TRAINING;
        zzavS[107] = KICK_SCOOTER;
        zzavS[42] = KICKBOXING;
        zzavS[43] = KITESURFING;
        zzavS[44] = MARTIAL_ARTS;
        zzavS[45] = MEDITATION;
        zzavS[46] = MIXED_MARTIAL_ARTS;
        zzavS[2] = ON_FOOT;
        zzavS[108] = OTHER;
        zzavS[47] = P90X;
        zzavS[48] = PARAGLIDING;
        zzavS[49] = PILATES;
        zzavS[50] = POLO;
        zzavS[51] = RACQUETBALL;
        zzavS[52] = ROCK_CLIMBING;
        zzavS[53] = ROWING;
        zzavS[54] = ROWING_MACHINE;
        zzavS[55] = RUGBY;
        zzavS[8] = RUNNING;
        zzavS[56] = RUNNING_JOGGING;
        zzavS[57] = RUNNING_SAND;
        zzavS[58] = RUNNING_TREADMILL;
        zzavS[59] = SAILING;
        zzavS[60] = SCUBA_DIVING;
        zzavS[61] = SKATEBOARDING;
        zzavS[62] = SKATING;
        zzavS[63] = SKATING_CROSS;
        zzavS[LocationRequest.PRIORITY_NO_POWER] = SKATING_INDOOR;
        zzavS[64] = SKATING_INLINE;
        zzavS[65] = SKIING;
        zzavS[66] = SKIING_BACK_COUNTRY;
        zzavS[67] = SKIING_CROSS_COUNTRY;
        zzavS[68] = SKIING_DOWNHILL;
        zzavS[69] = SKIING_KITE;
        zzavS[70] = SKIING_ROLLER;
        zzavS[71] = SLEDDING;
        zzavS[72] = SLEEP;
        zzavS[109] = SLEEP_LIGHT;
        zzavS[bd_provider.TUTORIAL_ID] = SLEEP_DEEP;
        zzavS[bd_provider.PRODUCTOS_ID] = SLEEP_REM;
        zzavS[bd_provider.FOTOS_ID] = SLEEP_AWAKE;
        zzavS[73] = SNOWBOARDING;
        zzavS[74] = SNOWMOBILE;
        zzavS[75] = SNOWSHOEING;
        zzavS[76] = SQUASH;
        zzavS[77] = STAIR_CLIMBING;
        zzavS[78] = STAIR_CLIMBING_MACHINE;
        zzavS[79] = STANDUP_PADDLEBOARDING;
        zzavS[3] = STILL;
        zzavS[80] = STRENGTH_TRAINING;
        zzavS[81] = SURFING;
        zzavS[82] = SWIMMING;
        zzavS[83] = SWIMMING_POOL;
        zzavS[84] = SWIMMING_OPEN_WATER;
        zzavS[85] = TABLE_TENNIS;
        zzavS[86] = TEAM_SPORTS;
        zzavS[87] = TENNIS;
        zzavS[5] = TILTING;
        zzavS[88] = TREADMILL;
        zzavS[4] = "unknown";
        zzavS[89] = VOLLEYBALL;
        zzavS[90] = VOLLEYBALL_BEACH;
        zzavS[91] = VOLLEYBALL_INDOOR;
        zzavS[92] = WAKEBOARDING;
        zzavS[7] = WALKING;
        zzavS[93] = WALKING_FITNESS;
        zzavS[94] = WALKING_NORDIC;
        zzavS[95] = WALKING_TREADMILL;
        zzavS[116] = WALKING_STROLLER;
        zzavS[96] = WATER_POLO;
        zzavS[97] = WEIGHTLIFTING;
        zzavS[98] = WHEELCHAIR;
        zzavS[99] = WINDSURFING;
        zzavS[100] = YOGA;
        zzavS[101] = ZUMBA;
    }

    FitnessActivities() {
    }

    public static String getMimeType(String activity) {
        return MIME_TYPE_PREFIX + activity;
    }

    public static String getName(int activity) {
        if (activity < 0 || activity >= zzavS.length) {
            return "unknown";
        }
        String str = zzavS[activity];
        return str == null ? "unknown" : str;
    }

    public static int zzdm(String str) {
        for (int i = 0; i < zzavS.length; i++) {
            if (zzavS[i].equals(str)) {
                return i;
            }
        }
        return 4;
    }
}
