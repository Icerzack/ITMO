import ru.ifmo.se.pokemon.*;


class ThunderWave extends StatusMove{
    protected ThunderWave(){
        super(Type.ELECTRIC, 0, 90);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        Effect.paralyze(p);
    }
    @Override
    protected String describe(){
        return "Парализует противника со 100% шансом.";
    }
}
class Discharge extends SpecialMove{
    protected Discharge(){
        super(Type.ELECTRIC, 80, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.3) Effect.paralyze(p);
    }
    @Override
    protected String describe(){
        return "Наносит урон и с шансом 30% парализует цель.";
    }
}
class Charm extends StatusMove{
    protected Charm(){
        super(Type.FAIRY, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.ATTACK, -2);
    }
    @Override
    protected String describe(){
        return "Наносит урон и имеет 30% вероятность заставить цель дрогнуть";
    }
}
class EerieImpulse extends StatusMove {
    protected EerieImpulse() {
        super(Type.ELECTRIC, 0, 100);
    }
    @Override
    protected void applyOppEffects (Pokemon p){
        p.setMod(Stat.SPECIAL_ATTACK, -2);
    }
    @Override
    protected String describe(){
        return "Понижает спец. атаку врага на 2";
    }
}
class DarkPulse extends SpecialMove {
    protected DarkPulse() {
        super(Type.DARK, 80, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.2) Effect.flinch(p);
    }
    @Override
    protected String describe(){
        return "Наносит урон и Имеет 20% вероятность заставить противника дрогнуть";
    }
}

class Psybeam extends SpecialMove {
    protected Psybeam(){
        super(Type.NORMAL, 65, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.1) Effect.confuse(p);
    }
    @Override
    protected String describe(){
        return "Наносит урон и Имеет 10% шанс привести цель в замешательство.";
    }
}

class Confide extends StatusMove {
    protected Confide(){
        super(Type.NORMAL, 0, 0);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.setMod(Stat.SPECIAL_ATTACK, -1);
    }
    @Override
    protected String describe(){
        return "Снижает спец. атаку цели на один пункт";
    }
}

class Thunderbolt extends SpecialMove {
    protected Thunderbolt(){
        super(Type.ELECTRIC, 90, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.1) Effect.paralyze(p);
    }
    @Override
    protected String describe(){
        return "Наносит урон и имеет 10% вероятность парализовать противника";
    }
}

class BrutalSwing extends PhysicalMove{
    protected BrutalSwing(){
        super(Type.DARK, 60, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        pokemon.setMod(Stat.HP, (int) Math.round(v));
    }
    @Override
    protected String describe(){
        return "Наносит урон и сильно раскачивает свое тело, чтобы нанести ущерб всему, что находится поблизости.";
    }
}

class Scald extends SpecialMove{
    protected Scald(){
        super(Type.WATER, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.3) Effect.burn(p);
    }
    @Override
    protected String describe(){
        return "Наносит урон и 30% шанс поджечь цель";
    }
}

class MuddyWater extends SpecialMove{
    protected MuddyWater(){
        super(Type.WATER, 90, 85);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.3) p.setMod(Stat.ACCURACY, -1);
    }
    @Override
    protected String describe(){
        return "Наносит урон и 30% шанс понизить точность цели на 1 ступень";
    }
}

class Superpower extends PhysicalMove{
    protected Superpower(){
        super(Type.FIGHTING, 120, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, -1);
        p.setMod(Stat.DEFENSE, -1);
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        pokemon.setMod(Stat.HP, (int) Math.round(v));
    }

    @Override
    protected String describe(){
        return "Наносит урон и С каждой атакой уменьшает у себя атаку и защиту на 1";
    }
}