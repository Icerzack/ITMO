import ru.ifmo.se.pokemon.*;

public class Dedenne extends Pokemon {
    public Dedenne(String name, int lvl){
        super(name, lvl);
        setStats(67, 58, 57, 81, 67, 101);
        setType(Type.ELECTRIC, Type.FAIRY);
        setMove(new ThunderWave(), new Discharge(), new Charm(), new EerieImpulse());

    }
}
