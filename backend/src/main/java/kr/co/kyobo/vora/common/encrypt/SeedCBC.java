package kr.co.kyobo.vora.common.encrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

public class SeedCBC {
    private static int[] SS0 = new int[]{696885672, 92635524, 382128852, 331600848, 340021332, 487395612, 747413676, 621093156, 491606364, 54739776, 403181592, 504238620, 289493328, 1020063996, 181060296, 591618912, 671621160, 71581764, 536879136, 495817116, 549511392, 583197408, 147374280, 386339604, 629514660, 261063564, 50529024, 994800504, 999011256, 318968592, 314757840, 785310444, 809529456, 210534540, 1057960764, 680042664, 839004720, 500027868, 919007988, 876900468, 751624428, 361075092, 185271048, 390550356, 474763356, 457921368, 1032696252, 16843008, 604250148, 470552604, 860058480, 411603096, 268439568, 214745292, 851636976, 432656856, 738992172, 667411428, 843215472, 58950528, 462132120, 297914832, 109478532, 164217288, 541089888, 272650320, 595829664, 734782440, 218956044, 914797236, 512660124, 256852812, 931640244, 441078360, 113689284, 944271480, 646357668, 302125584, 797942700, 365285844, 557932896, 63161280, 881111220, 21053760, 306336336, 1028485500, 227377548, 134742024, 521081628, 428446104, 0, 420024600, 67371012, 323179344, 935850996, 566354400, 1036907004, 910586484, 789521196, 654779172, 813740208, 193692552, 235799052, 730571688, 578986656, 776888940, 327390096, 223166796, 692674920, 1011642492, 151585032, 168428040, 1066382268, 802153452, 868479984, 96846276, 126321540, 335810580, 1053750012, 608460900, 516870876, 772678188, 189481800, 436867608, 101057028, 553722144, 726360936, 642146916, 33686016, 902164980, 310547088, 176849544, 202113036, 864269232, 1045328508, 281071824, 977957496, 122110788, 377918100, 633725412, 637936164, 8421504, 764256684, 533713884, 562143648, 805318704, 923218740, 781099692, 906375732, 352653588, 570565152, 940060728, 885321972, 663200676, 88424772, 206323788, 25264512, 701096424, 75792516, 394761108, 889532724, 197903304, 248431308, 1007431740, 826372464, 285282576, 130532292, 160006536, 893743476, 1003222008, 449499864, 952692984, 344232084, 424235352, 42107520, 80003268, 1070593020, 155795784, 956903736, 658989924, 12632256, 265274316, 398971860, 948482232, 252642060, 244220556, 37896768, 587408160, 293704080, 743202924, 466342872, 612671652, 872689716, 834793968, 138952776, 46318272, 793731948, 1024274748, 755835180, 4210752, 1049539260, 1041117756, 1015853244, 29475264, 713728680, 982168248, 240009804, 356864340, 990589752, 483184860, 675831912, 1062171516, 478974108, 415813848, 172638792, 373707348, 927429492, 545300640, 768467436, 105267780, 897954228, 722150184, 625303908, 986379000, 600040416, 965325240, 830583216, 529503132, 508449372, 969535992, 650568420, 847426224, 822161712, 717939432, 760045932, 525292380, 616882404, 817950960, 231588300, 143163528, 369496596, 973746744, 407392344, 348442836, 574775904, 688464168, 117900036, 855847728, 684253416, 453710616, 84214020, 961114488, 276861072, 709517928, 705307176, 445289112};
    private static int[] SS1 = new int[]{943196208, -399980320, 741149985, -1540979038, -871379005, -601960750, -1338801229, -1204254544, -1406169181, 1612726368, 1410680145, -1006123069, 1141130304, 1815039843, 1747667811, 1478183763, -1073495101, 1612857954, 808649523, -1271560783, 673777953, -1608482656, -534592798, -1540913245, -804011053, -1877900911, 269549841, 67503618, 471600144, -1136882512, 875955762, 1208699715, -332410909, -2012706688, 1814842464, -1473738592, 337053459, -1006320448, 336987666, -197868304, -1073560894, 1141196097, -534658591, -736704814, 1010765619, 1010634033, -1945203070, -1743222640, 673712160, 1276005954, -197736718, 1010699826, -1541044831, -130430479, 202181889, -601894957, -669464368, 673909539, 1680229986, 2017086066, 606537507, 741281571, -265174543, 1882342002, 1073889858, -736836400, 1073824065, -1073692480, 1882407795, 1680295779, -1406366560, -2012509309, -197670925, -1406300767, -2147450752, 471797523, -938816830, 741084192, -1473607006, 875824176, -804076846, 134941443, -332476702, -399914527, 1545424209, -1810594672, 404228112, -130496272, 1410811731, -1406234974, 134744064, -1006254655, 269681427, -871510591, -2079947134, -1204188751, -62926861, 2084392305, -1073626687, 808517937, -197802511, -2012575102, 1747602018, -1338932815, -804142639, 538968096, -736639021, 131586, 539099682, 67372032, 1747470432, 1882276209, 67569411, -669266989, -1675784815, -1743156847, 1612792161, -1136750926, -467220766, 1478052177, -602026543, 1343308113, -1877966704, -602092336, -1743091054, -1608285277, -1473541213, -804208432, -2147384959, 202313475, 1141327683, 404359698, -534527005, -332608288, -1945268863, -1136685133, -1810463086, 2017151859, 1545358416, -1608351070, -1608416863, 1612923747, 539165475, 1275940161, -938948416, -1675719022, -1675850608, 943327794, 202116096, 741215778, -1204122958, 1814974050, -1675653229, 1478117970, -265108750, -1877835118, -265042957, 1208568129, 2016954480, -871576384, 336921873, -130298893, 1882210416, 1949648241, 2084523891, 875889969, 269484048, 197379, 1680098400, 1814908257, -1006188862, 1949582448, -736770607, -1271626576, -399848734, 134809857, 1949714034, 404293905, -62992654, 1073758272, 269615634, -534724384, -1136816719, 67437825, -130364686, 65793, -265240336, 673843746, 1545490002, -1473672799, 1410745938, 1073955651, -2080012927, 336856080, -2012640895, -1743025261, -1338998608, -467286559, 1208502336, 2017020273, -1810397293, -63124240, 471731730, -2147319166, 539033889, -1945334656, 404425491, 1545555795, 1949779827, 1410614352, -1338867022, 471665937, 606405921, 1276071747, 0, 1141261890, -332542495, 1477986384, 1343373906, -399782941, 2084458098, -669332782, -938882623, -63058447, 808452144, -1810528879, 1680164193, 1010568240, -1271494990, -467352352, -1204057165, 2084326512, 202247682, 1343242320, 943262001, 606471714, 808583730, -2080078720, 1747536225, -1877769325, 876021555, -467154973, 606340128, -1541110624, -938751037, 1343439699, 134875650, -2079881341, -669398575, 1275874368, -2147253373, -1945137277, -871444798, 943393587, 1208633922, -1271429197};
    private static int[] SS2 = new int[]{-1582814839, -2122054267, -757852474, -741338173, 1347687492, 287055117, -1599329140, 556016901, 1364991309, 1128268611, 270014472, 303832590, 1364201793, -251904820, -1027077430, 1667244867, 539502600, 1078199364, 538976256, -1852039795, -522182464, -488627518, -1060632376, 320083719, -1583078011, -2087972977, 50332419, 1937259339, -1279771765, 319820547, -758115646, -487838002, 1886400576, -2138305396, 859586319, -1599592312, 842019330, -774103603, -218876218, 1886663748, -521392948, -1852566139, 50858763, 1398019911, 1348213836, 1398283083, -1313063539, 16777473, 539239428, 270277644, 1936732995, -1869080440, 269488128, -1060369204, -219139390, -774366775, 539765772, -471586873, 1919955522, -2088762493, -1818748021, -774893119, -2105276794, -1043854903, 1616912448, 1347424320, -1549786237, -471323701, 17566989, -1296812410, -1835262322, 1129058127, -1280034937, 1381505610, -1027340602, 1886926920, -1566300538, 303043074, -1548996721, -774629947, 1633689921, -1010826301, -1330367356, 1094713665, 1380979266, 1903967565, -2121527923, 526344, 320610063, -1852302967, 0, 286791945, 263172, 1397756739, -202098745, -505404991, -235127347, 1920218694, 590098191, 589571847, -1330630528, -2088236149, 34344462, -1549259893, -1566563710, 1651256910, -1819274365, 1095503181, 1634216265, 1887190092, 17303817, 34081290, -1279508593, -471060529, -202361917, -1044118075, -2088499321, 269751300, -218349874, 1617175620, -757326130, 573320718, 1128794955, 303569418, 33818118, 555753729, 1667771211, 1650730566, 33554946, -235653691, -1836051838, -2105013622, 789516, -1280298109, 1920745038, -791670592, 1920481866, 1128531783, -1835788666, -505141819, 572794374, -2139094912, -1582551667, -740548657, -1583341183, 808464384, 859059975, -1565774194, 842282502, 286528773, 572531202, 808990728, -252431164, -1549523065, 1094976837, 1078725708, -2122317439, -504878647, -2138831740, -1819011193, 825505029, -1010299957, -1026814258, 809253900, 1903178049, 286265601, -1010563129, -2121791095, 1903441221, -201835573, -757589302, -252167992, -1869343612, 1364728137, -2105539966, -1060895548, -201572401, 1095240009, 825768201, 1667508039, -1061158720, -1010036785, -741075001, -1330104184, 51121935, -2104750450, 1111491138, 589308675, -1852829311, 1617701964, -740811829, -1599855484, 808727556, -235916863, 1078462536, -1027603774, 1668034383, 826031373, 556543245, 1077936192, -1296286066, 842808846, -1329841012, -1044381247, -1566037366, -1296549238, 1112280654, 1364464965, 859323147, -790881076, 1617438792, 1937522511, -1868817268, -791144248, 1112017482, 1381242438, 1936996167, -1600118656, -504615475, 1111754310, -1313589883, 589835019, 1633953093, -218613046, -471850045, -1313326711, -1313853055, -1818484849, 1381768782, -235390519, -488364346, -1297075582, 825241857, -488101174, 1634479437, 1398546255, -521919292, -252694336, -1043591731, -2138568568, 303306246, 842545674, 1347950664, -791407420, 1650467394, 556280073, 50595591, 858796803, -521656120, 320346891, 17040645, 1903704393, -1869606784, 1650993738, 573057546, -1835525494};
    private static int[] SS3 = new int[]{137377848, -924784600, 220277805, -2036161498, -809251825, -825041890, -2085375949, -2001684424, -1885098961, 1080057888, 1162957845, -943471609, 1145062404, 1331915823, 1264805931, 1263753243, -1010581501, 1113743394, 53686323, -2051951563, 153167913, -2136956896, -1025318878, -2019318745, -1009528813, -2121166831, 17895441, 100795398, 202382364, -1934574532, 103953462, 1262700555, -807146449, -2004842488, 1281387564, -2002737112, 118690839, -993999868, 101848086, -990841804, -1027424254, 1161905157, -1042161631, -959261674, 255015999, 221330493, -1904047090, -2003789800, 136325160, 1312967694, -957156298, 238173246, -2053004251, -906889159, 218172429, -808199137, -925837288, 186853419, 1180853286, 1249015866, 119743527, 253963311, -1041108943, 1114796082, 1111638018, -992947180, 1094795265, -1061109760, 1131638835, 1197696039, -1935627220, -1954314229, -940313545, -1918784467, -2139062272, 252910623, -893204470, 203435052, -1969051606, 70267956, -1026371566, 184748043, -823989202, -907941847, 1297177629, -2070899692, 135272472, -923731912, 1196643351, -1901941714, 134219784, -977157115, 51580947, -842937331, -2038266874, -1984841671, -806093761, 1299283005, -1044267007, 20000817, -973999051, -1971156982, 1247963178, -2119061455, -1043214319, 2105376, -942418921, 33685506, 35790882, 67109892, 1214277672, 1097953329, 117638151, -875309029, -1919837155, -1986947047, 1096900641, -1900889026, -958208986, 1230067737, -841884643, 1095847953, -2138009584, -858727396, -1970104294, -2086428637, -1952208853, -1060057072, -2122219519, 251857935, 1195590663, 168957978, -1008476125, -857674708, -1920889843, -1884046273, -2037214186, 1265858619, 1280334876, -2103271390, -2120114143, 1130586147, 52633635, 1296124941, -926889976, -1902994402, -1936679908, 171063354, 201329676, 237120558, -1967998918, 1315073070, -1886151649, 1246910490, -1024266190, -2104324078, -1007423437, 1229015049, 1215330360, -859780084, 85005333, -873203653, 1081110576, 1165063221, 1332968511, 87110709, 1052688, 50528259, 1147167780, 1298230317, -960314362, 1148220468, -976104427, -2068794316, -891099094, 151062537, 1181905974, 152115225, -822936514, 1077952512, 34738194, -1059004384, -1917731779, 83952645, -890046406, 16842753, -1057951696, 170010666, 1314020382, -1985894359, 1179800598, 1128480771, -2055109627, 68162580, -1987999735, -1953261541, -2135904208, -975051739, 1212172296, 1232173113, -2020371433, -856622020, 236067870, -2105376766, 18948129, -1937732596, 185800731, 1330863135, 1198748727, 1146115092, -2102218702, 219225117, 86058021, 1329810447, 0, 1178747910, -840831955, 1213224984, 1112690706, -874256341, 1316125758, -892151782, -910047223, -839779267, 3158064, -2054056939, 1164010533, 204487740, -2035108810, -991894492, -1951156165, 1282440252, 235015182, 1079005200, 154220601, 102900774, 36843570, -2071952380, 1231120425, -2087481325, 120796215, -941366233, 69215268, -2069847004, -876361717, 1129533459, 167905290, -2021424121, -908994535, 1279282188, -2088534013, -1887204337, -826094578, 187906107, 1245857802, -2018266057};
    private static int[] KC = new int[]{-1640531527, 1013904243, 2027808486, -239350324, -478700647, -957401293, -1914802585, 465362127, 930724254, 1861448508, -572070280, -1144140559, 2006686179, -281594938, -563189875, -1126379749};
    private static Boolean LITTLE = false;
    private static Boolean BIG = true;
    private static Boolean ENDIAN;
    private int[] _RK = new int[48];
    private byte[] _IVEC = null;

    public static byte[] toByteArray(String var0) {
        byte[] var1 = (new BigInteger(var0, 16)).toByteArray();
        if (var1[0] == 0) {
            byte[] var2 = new byte[var1.length - 1];
            System.arraycopy(var1, 1, var2, 0, var2.length);
            return var2;
        } else {
            return var1;
        }
    }

    public static String toHexString(byte[] var0) {
        String var1 = "";
        int var2 = var0.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            var1 = var1 + String.format("%02X", var0[var3]);
        }

        return var1;
    }

    public static String clearTag(String var0) {
        StringBuffer var1 = new StringBuffer();
        int var2 = var0.indexOf(123);
        int var3 = var0.lastIndexOf(125);
        var0 = var0.substring(var2 + 1, var3);
        var0 = var0.replace(" ", "");
        String[] var4 = var0.split(",");
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            if (var7.length() == 4 && var7.startsWith("0x")) {
                var1.append(var7.substring(2));
            }
        }

        return var1.toString();
    }

    public SeedCBC() {
    }

    public String LoadConfig(String var1) {
        String var2 = "";
        String var3 = "";

        try {
            BufferedReader var4 = new BufferedReader(new FileReader(var1));
            int var6 = 0;

            while(true) {
                label24: {
                    String var5;
                    if ((var5 = var4.readLine()) != null) {
                        if (var6 == 0) {
                            var2 = var5;
                            break label24;
                        }

                        if (var6 == 1) {
                            var3 = var5;
                            break label24;
                        }
                    }

                    var4.close();
                    break;
                }

                ++var6;
            }
        } catch (IOException var7) {
            return "ERROR, KEY FILE Not Exist";
        }

        var2 = clearTag(var2);
        var3 = clearTag(var3);
        this._IVEC = toByteArray(var2);
        this.RoundKey(this._RK, toByteArray(var3));
        return "OK";
    }

    private int GetB0(int var1) {
        return 255 & var1;
    }

    private int GetB1(int var1) {
        return 255 & var1 >>> 8;
    }

    private int GetB2(int var1) {
        return 255 & var1 >>> 16;
    }

    private int GetB3(int var1) {
        return 255 & var1 >>> 24;
    }

    private void EndianChange(int[] var1) {
        var1[0] = var1[0] >>> 24 | var1[0] << 24 | var1[0] << 8 & 16711680 | var1[0] >>> 8 & '\uff00';
    }

    private int EndianChange(int var1) {
        return var1 >>> 24 | var1 << 24 | var1 << 8 & 16711680 | var1 >>> 8 & '\uff00';
    }

    public byte[] Encryption(byte[] var1) {
        int var2 = 16 - var1.length % 16;
        int var3 = var1.length + var2;
        byte[] var4 = new byte[var3];

        for(int var5 = 0; var5 < var3; ++var5) {
            if (var5 < var1.length) {
                var4[var5] = var1[var5];
            } else {
                var4[var5] = (byte)var2;
            }
        }

        this.CBCEnc(var4, var3);
        return var4;
    }

    public byte[] Decryption(byte[] var1) {
        this.CBCDec(var1, var1.length);
        byte var2 = var1[var1.length - 1];
        byte[] var3 = new byte[var1.length - var2];
        System.arraycopy(var1, 0, var3, 0, var3.length);
        return var3;
    }

    private void CBCEnc(byte[] var1, int var2) {
        int var3 = 0;

        for(byte[] var4 = (byte[])this._IVEC.clone(); var2 > 0; var3 += 16) {
            int var5;
            for(var5 = 0; var5 < 16; ++var5) {
                var4[var5] ^= var1[var3 + var5];
            }

            this.SEEDEncrypt(var4);

            for(var5 = 0; var5 < 16; ++var5) {
                var1[var3 + var5] = var4[var5];
            }

            var2 -= 16;
        }

    }

    private void CBCDec(byte[] var1, int var2) {
        int var3 = 0;
        byte[] var4 = (byte[])this._IVEC.clone();

        for(byte[] var5 = new byte[16]; var2 >= 16; var3 += 16) {
            int var6;
            for(var6 = 0; var6 < 16; ++var6) {
                var5[var6] = var1[var3 + var6];
            }

            this.SEEDDecrypt(var5);

            for(var6 = 0; var6 < 16; ++var6) {
                var5[var6] ^= var4[var6];
                var4[var6] = var1[var3 + var6];
                var1[var3 + var6] = var5[var6];
            }

            var2 -= 16;
        }

    }

    private void SeedRound(int[] var1, int[] var2, int[] var3, int[] var4, int[] var5) {
        long var8 = 0L;
        long var10 = 0L;
        int var6 = var3[0] ^ var5[0];
        int var7 = var4[0] ^ var5[1];
        var7 ^= var6;
        var8 = var6 < 0 ? (long)(var6 & 2147483647) | -2147483648L : (long)var6;
        var7 = SS0[this.GetB0(var7)] ^ SS1[this.GetB1(var7)] ^ SS2[this.GetB2(var7)] ^ SS3[this.GetB3(var7)];
        var10 = var7 < 0 ? (long)(var7 & 2147483647) | -2147483648L : (long)var7;
        var8 += var10;
        var6 = SS0[this.GetB0((int)var8)] ^ SS1[this.GetB1((int)var8)] ^ SS2[this.GetB2((int)var8)] ^ SS3[this.GetB3((int)var8)];
        var8 = var6 < 0 ? (long)(var6 & 2147483647) | -2147483648L : (long)var6;
        var10 += var8;
        var7 = SS0[this.GetB0((int)var10)] ^ SS1[this.GetB1((int)var10)] ^ SS2[this.GetB2((int)var10)] ^ SS3[this.GetB3((int)var10)];
        var10 = var7 < 0 ? (long)(var7 & 2147483647) | -2147483648L : (long)var7;
        var8 += var10;
        var1[0] ^= (int)var8;
        var2[0] ^= (int)var10;
    }

    private void SEEDEncrypt(byte[] var1) {
        int[] var2 = new int[1];
        int[] var3 = new int[1];
        int[] var4 = new int[1];
        int[] var5 = new int[1];
        var2[0] = 0;
        var3[0] = 0;
        var4[0] = 0;
        var5[0] = 0;
        int[] var6 = new int[2];
        byte var7 = 0;
        var2[0] = var1[0] & 255;
        var2[0] = var2[0] << 8 ^ var1[1] & 255;
        var2[0] = var2[0] << 8 ^ var1[2] & 255;
        var2[0] = var2[0] << 8 ^ var1[3] & 255;
        var3[0] = var1[4] & 255;
        var3[0] = var3[0] << 8 ^ var1[5] & 255;
        var3[0] = var3[0] << 8 ^ var1[6] & 255;
        var3[0] = var3[0] << 8 ^ var1[7] & 255;
        var4[0] = var1[8] & 255;
        var4[0] = var4[0] << 8 ^ var1[9] & 255;
        var4[0] = var4[0] << 8 ^ var1[10] & 255;
        var4[0] = var4[0] << 8 ^ var1[11] & 255;
        var5[0] = var1[12] & 255;
        var5[0] = var5[0] << 8 ^ var1[13] & 255;
        var5[0] = var5[0] << 8 ^ var1[14] & 255;
        var5[0] = var5[0] << 8 ^ var1[15] & 255;
        if (!ENDIAN) {
            this.EndianChange(var2);
            this.EndianChange(var3);
            this.EndianChange(var4);
            this.EndianChange(var5);
        }

        int var9 = var7 + 1;
        var6[0] = this._RK[var7];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[0] = this._RK[var9++];
        var6[1] = this._RK[var9++];
        this.SeedRound(var4, var5, var2, var3, var6);
        if (!ENDIAN) {
            this.EndianChange(var2);
            this.EndianChange(var3);
            this.EndianChange(var4);
            this.EndianChange(var5);
        }

        for(int var8 = 0; var8 < 4; ++var8) {
            var1[var8] = (byte)(var4[0] >>> 8 * (3 - var8) & 255);
            var1[4 + var8] = (byte)(var5[0] >>> 8 * (3 - var8) & 255);
            var1[8 + var8] = (byte)(var2[0] >>> 8 * (3 - var8) & 255);
            var1[12 + var8] = (byte)(var3[0] >>> 8 * (3 - var8) & 255);
        }

    }

    private void SEEDDecrypt(byte[] var1) {
        int[] var2 = new int[1];
        int[] var3 = new int[1];
        int[] var4 = new int[1];
        int[] var5 = new int[1];
        int[] var6 = new int[2];
        var2[0] = 0;
        var3[0] = 0;
        var4[0] = 0;
        var5[0] = 0;
        byte var7 = 31;
        var2[0] = var1[0] & 255;
        var2[0] = var2[0] << 8 ^ var1[1] & 255;
        var2[0] = var2[0] << 8 ^ var1[2] & 255;
        var2[0] = var2[0] << 8 ^ var1[3] & 255;
        var3[0] = var1[4] & 255;
        var3[0] = var3[0] << 8 ^ var1[5] & 255;
        var3[0] = var3[0] << 8 ^ var1[6] & 255;
        var3[0] = var3[0] << 8 ^ var1[7] & 255;
        var4[0] = var1[8] & 255;
        var4[0] = var4[0] << 8 ^ var1[9] & 255;
        var4[0] = var4[0] << 8 ^ var1[10] & 255;
        var4[0] = var4[0] << 8 ^ var1[11] & 255;
        var5[0] = var1[12] & 255;
        var5[0] = var5[0] << 8 ^ var1[13] & 255;
        var5[0] = var5[0] << 8 ^ var1[14] & 255;
        var5[0] = var5[0] << 8 ^ var1[15] & 255;
        if (!ENDIAN) {
            this.EndianChange(var2);
            this.EndianChange(var3);
            this.EndianChange(var4);
            this.EndianChange(var5);
        }

        int var9 = var7 - 1;
        var6[1] = this._RK[var7];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var4, var5, var2, var3, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9--];
        this.SeedRound(var2, var3, var4, var5, var6);
        var6[1] = this._RK[var9--];
        var6[0] = this._RK[var9];
        this.SeedRound(var4, var5, var2, var3, var6);
        if (!ENDIAN) {
            this.EndianChange(var2);
            this.EndianChange(var3);
            this.EndianChange(var4);
            this.EndianChange(var5);
        }

        for(int var8 = 0; var8 < 4; ++var8) {
            var1[var8] = (byte)(var4[0] >>> 8 * (3 - var8) & 255);
            var1[4 + var8] = (byte)(var5[0] >>> 8 * (3 - var8) & 255);
            var1[8 + var8] = (byte)(var2[0] >>> 8 * (3 - var8) & 255);
            var1[12 + var8] = (byte)(var3[0] >>> 8 * (3 - var8) & 255);
        }

    }

    private void EncRoundKeyUpdate0(int[] var1, int[] var2, int[] var3, int[] var4, int[] var5, int var6) {
        int var7 = var2[0];
        var2[0] = var2[0] >>> 8 ^ var3[0] << 24;
        var3[0] = var3[0] >>> 8 ^ var7 << 24;
        int var9 = var2[0] + var4[0] - KC[var6];
        int var10 = var3[0] + KC[var6] - var5[0];
        var1[0] = SS0[this.GetB0(var9)] ^ SS1[this.GetB1(var9)] ^ SS2[this.GetB2(var9)] ^ SS3[this.GetB3(var9)];
        var1[1] = SS0[this.GetB0(var10)] ^ SS1[this.GetB1(var10)] ^ SS2[this.GetB2(var10)] ^ SS3[this.GetB3(var10)];
    }

    private void EncRoundKeyUpdate1(int[] var1, int[] var2, int[] var3, int[] var4, int[] var5, int var6) {
        int var7 = var4[0];
        var4[0] = var4[0] << 8 ^ var5[0] >>> 24;
        var5[0] = var5[0] << 8 ^ var7 >>> 24;
        int var9 = var2[0] + var4[0] - KC[var6];
        int var10 = var3[0] + KC[var6] - var5[0];
        var1[0] = SS0[this.GetB0(var9)] ^ SS1[this.GetB1(var9)] ^ SS2[this.GetB2(var9)] ^ SS3[this.GetB3(var9)];
        var1[1] = SS0[this.GetB0(var10)] ^ SS1[this.GetB1(var10)] ^ SS2[this.GetB2(var10)] ^ SS3[this.GetB3(var10)];
    }

    private void RoundKey(int[] var1, byte[] var2) {
        int[] var3 = new int[1];
        int[] var4 = new int[1];
        int[] var5 = new int[1];
        int[] var6 = new int[1];
        int[] var7 = new int[2];
        byte var10 = 2;
        var3[0] = var2[0] & 255;
        var3[0] = var3[0] << 8 ^ var2[1] & 255;
        var3[0] = var3[0] << 8 ^ var2[2] & 255;
        var3[0] = var3[0] << 8 ^ var2[3] & 255;
        var4[0] = var2[4] & 255;
        var4[0] = var4[0] << 8 ^ var2[5] & 255;
        var4[0] = var4[0] << 8 ^ var2[6] & 255;
        var4[0] = var4[0] << 8 ^ var2[7] & 255;
        var5[0] = var2[8] & 255;
        var5[0] = var5[0] << 8 ^ var2[9] & 255;
        var5[0] = var5[0] << 8 ^ var2[10] & 255;
        var5[0] = var5[0] << 8 ^ var2[11] & 255;
        var6[0] = var2[12] & 255;
        var6[0] = var6[0] << 8 ^ var2[13] & 255;
        var6[0] = var6[0] << 8 ^ var2[14] & 255;
        var6[0] = var6[0] << 8 ^ var2[15] & 255;
        if (!ENDIAN) {
            var3[0] = this.EndianChange(var3[0]);
            var4[0] = this.EndianChange(var4[0]);
            var5[0] = this.EndianChange(var5[0]);
            var6[0] = this.EndianChange(var6[0]);
        }

        int var8 = var3[0] + var5[0] - KC[0];
        int var9 = var4[0] - var6[0] + KC[0];
        var1[0] = SS0[this.GetB0(var8)] ^ SS1[this.GetB1(var8)] ^ SS2[this.GetB2(var8)] ^ SS3[this.GetB3(var8)];
        var1[1] = SS0[this.GetB0(var9)] ^ SS1[this.GetB1(var9)] ^ SS2[this.GetB2(var9)] ^ SS3[this.GetB3(var9)];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 1);
        int var11 = var10 + 1;
        var1[var10] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate1(var7, var3, var4, var5, var6, 2);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 3);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate1(var7, var3, var4, var5, var6, 4);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 5);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate1(var7, var3, var4, var5, var6, 6);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 7);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate1(var7, var3, var4, var5, var6, 8);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 9);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate1(var7, var3, var4, var5, var6, 10);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 11);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate1(var7, var3, var4, var5, var6, 12);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 13);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate1(var7, var3, var4, var5, var6, 14);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
        this.EncRoundKeyUpdate0(var7, var3, var4, var5, var6, 15);
        var1[var11++] = var7[0];
        var1[var11++] = var7[1];
    }

    static {
        ENDIAN = BIG;
    }

    private static SeedCBC cbc = new SeedCBC();

    public static String getDecode(String str) {
        cbc.LoadConfig("key.dat");
        return new String(cbc.Decryption(Base64.getDecoder().decode(str)));
    }

    public static String getEncode(String str) {
        cbc.LoadConfig("key.dat");
        return Base64.getEncoder().encodeToString(cbc.Encryption(str.getBytes()));
    }
}