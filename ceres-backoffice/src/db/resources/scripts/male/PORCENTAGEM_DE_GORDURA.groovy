if (COM_ADIPOMETRO)
    1.1714D - 0.0671D * Math.log10(TRICEPS + SUPRA_ILIACA + ABDOMEN)
else
    495 / (1.29579 - 0.350 * (Math.log10(CINTURA + QUADRIL - PESCOCO) + 0.22100 * Math.log10(ALTURA))) - 450
