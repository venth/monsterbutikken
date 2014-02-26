package no.borber.monsterShop.monsterTypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MonsterTypesRepo {

    private final static Map<String,MonsterTypeJson> monsterTypes = new HashMap<>();

    static  {
        monsterTypes.put("Ao (skilpadde)", new MonsterTypeJson("Ao (skilpadde)", 100000));
        monsterTypes.put("Bakeneko", new MonsterTypeJson("Bakeneko", 120000));
        monsterTypes.put("Basilisk", new MonsterTypeJson("Basilisk", 175000));
        monsterTypes.put("Det erymanthiske villsvin", new MonsterTypeJson("Det erymanthiske villsvin", 100));
        monsterTypes.put("Griff", new MonsterTypeJson("Griff", 100));
        monsterTypes.put("Hamløper", new MonsterTypeJson("Hamløper", 100));
        monsterTypes.put("Hippogriff", new MonsterTypeJson("Hippogriff", 100));
        monsterTypes.put("Hydra", new MonsterTypeJson("Hydra", 100));
        monsterTypes.put("Kentaur", new MonsterTypeJson("Kentaur", 100));
        monsterTypes.put("Kerberos", new MonsterTypeJson("Kerberos", 100));
        monsterTypes.put("Kraken", new MonsterTypeJson("Kraken", 100));
        monsterTypes.put("Mannbjørn", new MonsterTypeJson("Mannbjørn", 100));
        monsterTypes.put("Mantikora", new MonsterTypeJson("Mantikora", 100));
        monsterTypes.put("Margyge", new MonsterTypeJson("Margyge", 100));
        monsterTypes.put("Marmæle", new MonsterTypeJson("Marmæle", 100));
        monsterTypes.put("Minotauros", new MonsterTypeJson("Minotauros", 100));
        monsterTypes.put("Nekomusume", new MonsterTypeJson("Nekomusume", 100));
        monsterTypes.put("Rokk", new MonsterTypeJson("Rokk", 100));
        monsterTypes.put("Seljordsormen", new MonsterTypeJson("Seljordsormen", 100));
        monsterTypes.put("Sfinks", new MonsterTypeJson("Sfinks", 100));
        monsterTypes.put("Sirene", new MonsterTypeJson("Sirene", 100));
        monsterTypes.put("Sjøorm", new MonsterTypeJson("Sjøorm", 100));
        monsterTypes.put("Succubus", new MonsterTypeJson("Succubus", 100));
        monsterTypes.put("Valravn", new MonsterTypeJson("Valravn", 100));
        monsterTypes.put("Vampyr", new MonsterTypeJson("Vampyr", 100));
        monsterTypes.put("Varulv", new MonsterTypeJson("Varulv", 100));
    }

    public static Set<MonsterTypeJson> getMonsterTypes() {
        return new HashSet<>(monsterTypes.values());
    }

    public static MonsterTypeJson getMonsterType(String monsterType) {
        return monsterTypes.get(monsterType);
    }
}
