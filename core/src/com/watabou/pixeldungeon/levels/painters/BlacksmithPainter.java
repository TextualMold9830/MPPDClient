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

import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.mobs.npcs.Blacksmith;
import com.watabou.pixeldungeon.items.Generator;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.utils.Random;

public class BlacksmithPainter extends Painter {

	@Override
	public void paint( Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.FIRE_TRAP );
		fill( level, room, 2, Terrain.EMPTY_SP );
		
		for (int i=0; i < 2; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY_SP);
			level.drop( 
				Generator.random( Random.oneOf( 
					Generator.Category.ARMOR, 
					Generator.Category.WEAPON
				) ), pos );
		}
		
		for (Room.Door door : room.connected.values()) {
			door.set( Room.Door.Type.UNLOCKED );
			drawInside( level, room, door, 1, Terrain.EMPTY );
		}
		
		Blacksmith npc = new Blacksmith();
		do {
			npc.pos = room.random( 1 );
		} while (level.heaps.get( npc.pos ) != null);
		level.mobs.add( npc );
		Actor.occupyCell( npc );
	}
}
