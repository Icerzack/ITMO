import ru.ifmo.se.pokemon.*;

public class Malamar extends Inkay {
    public Malamar(String name, int lvl){
        super(name, lvl);
        setStats(86, 92, 88, 68, 75, 73);
        setType(Type.DARK, Type.PSYCHIC);
        setMove(new Thunderbolt(), new Psybeam(), new DarkPulse(), new BrutalSwing());
    }
}
