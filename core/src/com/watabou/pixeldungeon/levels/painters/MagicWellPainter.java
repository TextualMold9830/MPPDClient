/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.levels.painters;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.blobs.WaterOfAwareness;
import com.watabou.pixeldungeon.actors.blobs.WaterOfHealth;
import com.watabou.pixeldungeon.actors.blobs.WaterOfTransmutation;
import com.watabou.pixeldungeon.actors.blobs.WellWater;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class MagicWellPainter extends Painter {

	private static final Class<?>[] WATERS = 
		{WaterOfAwareness.class, WaterOfHealth.class, WaterOfTransmutation.class};
	
	@Override
	public void paint( Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY );
		
		Point c = room.center();
		set( level, c.x, c.y, Terrain.WELL );
		
		@SuppressWarnings("unchecked")
		Class<? extends WellWater> waterClass = 
			(Class<? extends WellWater>)Random.element( WATERS );
		
		WellWater water = (WellWater)level.blobs.get( waterClass );
		if (water == null) {
			try {
				water = ClassReflection.newInstance(waterClass);
			} catch (Exception e) {
				water = null;
			}
		}
		water.seed( c.x + Level.WIDTH * c.y, 1 );
		level.blobs.put( waterClass, water );
		
		room.entrance().set( Room.Door.Type.REGULAR );
	}
}
