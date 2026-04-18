/*
 *  Copyright (c) 2026 Cranberry Platypus <cranberryplatypus968@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 3 of the License, or (at your option) any later
 *  version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ichi2.compat

import android.view.Window
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsCompat

@RequiresApi(30)
@Suppress("ktlint:standard:property-naming")
open class CompatV30 : CompatV29() {
    // As of API30, insetsController is the correct way to hide the status bar
    override fun hideStatusBar(window: Window) {
        window.insetsController?.hide(WindowInsetsCompat.Type.statusBars())
    }
}
