import ru.ifmo.se.pokemon.*;

public class Azumarill extends Marill {
    public Azumarill(String name, int lvl){
        super(name, lvl);
        setStats(100, 50, 80, 60, 80, 50);
        setType(Type.WATER, Type.FAIRY);
        setMove(new Confide(), new Scald(), new MuddyWater(), new Superpower());
    }
}
