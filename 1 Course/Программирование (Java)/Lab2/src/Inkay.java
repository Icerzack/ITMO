import ru.ifmo.se.pokemon.*;

public class Inkay extends Pokemon {
    public Inkay(String name, int lvl){
        super(name, lvl);
        setStats(53, 54, 53, 37, 46, 45);
        setType(Type.DARK, Type.PSYCHIC);
        setMove(new DarkPulse(), new Psybeam(), new Thunderbolt());
    }
}
