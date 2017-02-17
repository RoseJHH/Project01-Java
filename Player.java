
public class Player {
    public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix}

    ;

    private PlayerType type;    //Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
    private double maxHP;        //Max HP of this player
    private double currentHP;    //Current HP of this player
    private double atk;            //Attack power of this player
    private boolean isCursed;
    private boolean isTaunt;
    private int numSpecialTurn;
    private int numSpecial =1;
    private Player target;
    public Player[][] myteam;
    public Player[][] theirteam;
    private double minHP;
    private String position;


    /**
     * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns,
     * as specified in the given table. It also reset the internal turn count of this player.
     *
     * @param _type
     */
    public Player(PlayerType _type) {
        this.isCursed = false;
        this.isTaunt = false;
        this.type=_type;
        if (this.type == PlayerType.Healer) {
            this.maxHP = 4790;
            this.atk = 238;
            this.numSpecialTurn = 4;
            this.currentHP=this.maxHP;
        } else if (this.type == PlayerType.Tank) {
            this.maxHP = 6240;
            this.atk = 315;
            this.numSpecialTurn = 4;
            this.currentHP=this.maxHP;
        } else if (this.type == PlayerType.Samurai) {
            this.maxHP = 4905;
            this.atk = 368;
            this.numSpecialTurn = 3;
            this.currentHP=this.maxHP;
        } else if (this.type == PlayerType.BlackMage) {
            this.maxHP = 4175;
            this.atk = 339;
            this.numSpecialTurn = 4;
            this.currentHP=this.maxHP;
        } else if (this.type == PlayerType.Phoenix) {
            this.maxHP = 5175;
            this.atk = 209;
            this.numSpecialTurn = 8;
            this.currentHP=this.maxHP;
        }
    }

    /**
     * Returns the current HP of this player
     *
     * @return
     */
    public double getCurrentHP() {
        //INSERT YOUR CODE HERE
        return this.currentHP;
    }
//    public double getnumSpecialTurn() {
//        //INSERT YOUR CODE HERE
//        return numSpecialTurn;
//    }

    /**
     * Returns type of this player
     *
     * @return
     */
    public Player.PlayerType getType() {
        //INSERT YOUR CODE HERE
        return type;
    }

    /**
     * Returns max HP of this player.
     *
     * @return
     */
    public double getMaxHP() {
        //INSERT YOUR CODE HERE

        return maxHP;
    }


    /**
     * Returns whether this player is being cursed.
     *
     * @return
     */
    public boolean isCursed() {
        //INSERT YOUR CODE HERE

        return this.isCursed;
    }

    /**
     * Returns whether this player is alive (i.e. current HP > 0).
     *
     * @return
     */
    public boolean isAlive() {
        //INSERT YOUR CODE HERE
        if (currentHP <= 0) {
            return false;
        }

        return true;
    }

    /**
     * Returns whether this player is taunting the other team.
     *
     * @return
     */
    public boolean isTaunting() {
        //INSERT YOUR CODE HERE

        return this.isTaunt;
    }


    public void attack(Player target) {
        //INSERT YOUR CODE HERE
        if(this.isAlive()) {
            target.currentHP -= this.atk;
            if (target.currentHP < 0) {
                target.currentHP = 0;


            }
        }
    }

    public Player findHealing(Player[][] team) {
        Player min = null;
        minHP = 1;
        for (int k = 0; k < 2; k++) {
            for (int l = 0; l < Arena.numRowPlayer; l++) {
                if (team[k][l].getCurrentHP()/team[k][l].maxHP<= minHP && team[k][l].isCursed == false&& team[k][l].isAlive()) {
                    minHP = team[k][l].getCurrentHP()/team[k][l].maxHP;
                    min = team[k][l];
                }
            }
        }
        return min;
    }

    public Player checktaunt(Player[][] team) {
        Player Taunt = null;
        for (int k = 0; k < 2; k++) {
            for (int l = 0; l < Arena.numRowPlayer; l++) {
                if (team[k][l].isTaunt == true&&team[k][l].isAlive()) {
                    Taunt = team[k][l];
                    break;
                }
            }
            if(Taunt!=null) break;
        }

        return Taunt;
    }

    public Player findtarget(Player[][] team) {
        if (checktaunt(team) == null) {
//            minHP = team[0][0].getCurrentHP();
//            for (int k = 0; k < 2; k++) {
//                for (int l = 0; l < Arena.numRowPlayer; l++) {
//                    if (team[k][l].getCurrentHP() <= minHP &&team[k][l].isAlive()) {
//                        minHP = team[k][l].getCurrentHP();
//                        this.target = team[k][l];
//                    }
//                }
//            }

            Player minHPeiei = null;
            minHP = 10000000;
            for(int i=0;i<2;i++){
                for(int j=0;j< Arena.numRowPlayer;j++){
                    if(team[i][j].isAlive()&&team[i][j].getCurrentHP() < minHP )
                    {
                        minHP=team[i][j].getCurrentHP();
                        minHPeiei=team[i][j];

                    }
                }
                if(minHPeiei!=null){
                    break;
                }
            }

        } else {
            this.target = checktaunt(team);
        }

        return this.target;
    }



    public Player finddead(Player[][] team) {
        Player dead = null;
        for (int k = 0; k < 2; k++) {
            for (int l = 0; l < Arena.numRowPlayer; l++) {
                if (team[k][l].isAlive() == false) {
                    return dead = team[k][l];
                }
            }
        }

        return null;
    }
    public void reTaunt(){
        this.isTaunt=false;
    }
     public void reCurse(){
         this.isCursed=false;
     }
    public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam) {
        //INSERT YOUR CODE HERE

        if (type == PlayerType.Healer) {
                Player heal =findHealing(myteam);
                //System.out.println(heal);
                if(heal.currentHP < heal.maxHP) {
                    heal.currentHP += 0.3 * heal.maxHP;
                    if (heal.currentHP > heal.maxHP) {
                        heal.currentHP = heal.maxHP;

                    }
                    System.out.println("# " + this.position + " Heals " + heal.position);
                }



        } else if (type == PlayerType.Tank) {
            this.isTaunt = true;
            System.out.println("# "+this.position+" is Taunting");

        } else if (type == PlayerType.Samurai) {
//            System.out.println(this.checktaunt(this.theirteam));
            if(this.checktaunt(this.theirteam)==null){
                this.attack(this.target);
                this.attack(this.target);
                System.out.println("# "+this.position+" Double-Slashes "+this.target.position);
            }else {
                Player target =this.checktaunt(this.theirteam);
                this.attack(target);
                this.attack(target);
                System.out.println("# "+this.position+" Double-Slashes "+target.position);
            }



        } else if (type == PlayerType.BlackMage) {
            findtarget(theirTeam).isCursed = true;
            System.out.println("# "+this.position+" Curses "+findtarget(theirTeam).position);

        } else if (type == PlayerType.Phoenix) {
            if(finddead(myteam)!=null) {
                finddead(myteam).numSpecial = 1;
                finddead(myteam).reCurse();
                finddead(myteam).reTaunt();
                System.out.println("# "+this.position+" Revives "+finddead(myteam).position);
                finddead(myteam).currentHP += 0.3 * finddead(myTeam).maxHP;
                this.numSpecial = 0;
            }
        }

    }
    public void setPosition(Arena.Team team, Player.PlayerType pType, Arena.Row row, int position){
         this.position=team+"["+row+"]"+"["+position+"]"+" "+"{"+pType+"}";
    }


    /**
     * This method is called by Arena when it is this player's turn to take an action.
     * By default, the player simply just "attack(target)". However, once this player has
     * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
     * where each player type performs his own special move.
     *
     * @param arena
     */
    public void takeAction(Arena arena) {
        //this.myteamminHP = arena.myteamminHP;
        //this.attack(arena.target);

//        this.numSpecial++;
//        if (this.numSpecial % this.numSpecialTurn==0) {
//            this.useSpecialAbility(arena.myteam, arena.theirteam);
//        } else {
//            this.attack(arena.target);
//        }
        if(this.isAlive()==true) {
            this.target = arena.target;
            this.myteam = arena.myteam;
            this.theirteam = arena.theirteam;
            // System.out.println(this.getType()+" attack "+this.target+" HP target"+this.target.currentHP);

            //System.out.println(Arena.);
            //INSERT YOUR CODE HERE

            if (this.numSpecial == this.numSpecialTurn) {
                this.useSpecialAbility(arena.myteam, arena.theirteam);
                this.numSpecial = 0;
                // System.out.println(this.getType()+" attack "+this.target+" HP target"+this.target.currentHP);
            } else {
                if (this.checktaunt(this.theirteam) == null) {
                    System.out.println("# "+this.position+ " Attacks "+arena.target.position);
                    this.attack(arena.target);

                } else {
                    Player target = this.checktaunt(this.theirteam);
                    this.attack(target);
                    System.out.println("# "+this.position + " Attacks " + target.position);

                }

                //System.out.println(this.getType()+" attack "+this.target+" HP target"+this.target.currentHP);
            }
            this.numSpecial++;
        }


    }

    /**
     * This method overrides the default Object's toString() and is already implemented for you.
     */
    @Override
    public String toString() {
        return "[" + this.type + " HP:" + this.currentHP + "/" + this.maxHP + " ATK:" + this.atk + "]";
    }

}
