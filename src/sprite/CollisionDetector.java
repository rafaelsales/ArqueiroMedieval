package sprite;

public class CollisionDetector {

	public static boolean colisaoEntre(Monstro monstro, Flecha flecha) {

		double meioFlecha = flecha.x + flecha.width / 2d;
		double meioMonstro = monstro.x + monstro.width / 2d;

		if (meioFlecha - meioMonstro < flecha.getVelocidade()
				&& meioMonstro - meioFlecha < flecha.getVelocidade()
				&& flecha.y - 10 > monstro.y
				&& flecha.y + 15 < monstro.y + monstro.height)
			return true;
		
		return false;
	}

	public static boolean colisaoEntre(Monstro monstro, Arqueiro arqueiro) {
		if (monstro.x == arqueiro.x + (arqueiro.width / 2))
			return true;
		return false;
	}
	
	
	
}
