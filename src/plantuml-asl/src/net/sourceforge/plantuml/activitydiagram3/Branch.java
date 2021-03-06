/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  https://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * https://plantuml.com/patreon (only 1$ per month!)
 * https://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.activitydiagram3;

import java.util.Collection;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.UseStyle;
import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.activitydiagram3.ftile.WeldingPoint;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.Rainbow;
import net.sourceforge.plantuml.graphic.color.Colors;
import net.sourceforge.plantuml.sequencediagram.NotePosition;
import net.sourceforge.plantuml.sequencediagram.NoteType;
import net.sourceforge.plantuml.style.PName;
import net.sourceforge.plantuml.style.SName;
import net.sourceforge.plantuml.style.Style;
import net.sourceforge.plantuml.style.StyleBuilder;
import net.sourceforge.plantuml.style.StyleSignature;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class Branch {

	private final InstructionList list;
	private final Display labelTest;

	private final LinkRendering labelPositive;

	private LinkRendering inlinkRendering = LinkRendering.none();
	private final LinkRendering inlabel;
	private LinkRendering special;

	private final HColor color;

	private Ftile ftile;

	public StyleSignature getDefaultStyleDefinitionArrow() {
		return StyleSignature.of(SName.root, SName.element, SName.activityDiagram, SName.arrow);
	}

	public StyleSignature getDefaultStyleDefinitionDiamond() {
		return StyleSignature.of(SName.root, SName.element, SName.activityDiagram, SName.activity, SName.diamond);
	}

	public boolean containsBreak() {
		return list.containsBreak();
	}

	public Branch(StyleBuilder styleBuilder, Swimlane swimlane, LinkRendering labelPositive, Display labelTest,
			HColor color, LinkRendering inlabel) {
		if (labelPositive == null) {
			throw new IllegalArgumentException();
		}
		if (labelTest == null) {
			throw new IllegalArgumentException();
		}
		if (inlabel == null) {
			throw new IllegalArgumentException();
		}
		if (UseStyle.useBetaStyle()) {
			final Style style = getDefaultStyleDefinitionDiamond().getMergedStyle(styleBuilder);
			this.color = color == null
					? style.value(PName.BackGroundColor).asColor(styleBuilder.getSkinParam().getIHtmlColorSet())
					: color;
		} else {
			this.color = color;
		}

		this.inlabel = inlabel;
		this.list = new InstructionList(swimlane);
		this.labelTest = labelTest;
		this.labelPositive = labelPositive;
	}

	public Collection<WeldingPoint> getWeldingPoints() {
		return ftile.getWeldingPoints();
	}

	public void add(Instruction ins) {
		list.add(ins);
	}

	public boolean kill() {
		return list.kill();
	}

	public boolean addNote(Display note, NotePosition position, NoteType type, Colors colors, Swimlane swimlaneNote) {
		return list.addNote(note, position, type, colors, swimlaneNote);
	}

	public final void setInlinkRendering(LinkRendering inlinkRendering) {
		if (inlinkRendering == null) {
			throw new IllegalArgumentException();
		}
		this.inlinkRendering = inlinkRendering;
	}

	public void updateFtile(FtileFactory factory) {
		this.ftile = factory.decorateOut(list.createFtile(factory), inlinkRendering);
	}

	public Collection<? extends Swimlane> getSwimlanes() {
		return list.getSwimlanes();
	}

	public final Display getLabelPositive() {
		final LinkRendering in = ftile.getInLinkRendering();
		if (in != null && Display.isNull(in.getDisplay()) == false) {
			return in.getDisplay();
		}
		return labelPositive.getDisplay();
	}

	public final Display getLabelTest() {
		return labelTest;
	}

	public final Rainbow getOut() {
		if (special != null) {
			return special.getRainbow();
		}
//		if (labelPositive.getRainbow().size() > 0) {
//			return labelPositive.getRainbow();
//		}
		if (inlinkRendering == null) {
			return null;
		}
		return inlinkRendering.getRainbow();
	}

	public Rainbow getInColor(Rainbow arrowColor) {
		if (isEmpty()) {
			return getFtile().getOutLinkRendering().getRainbow(arrowColor);
		}
		if (labelPositive.getRainbow().size() > 0) {
			return labelPositive.getRainbow();
		}
		final LinkRendering linkIn = getFtile().getInLinkRendering();
		final Rainbow color = linkIn.getRainbow(arrowColor);
		if (color.size() == 0) {
			return arrowColor;
		}
		return color;
	}

	public Display getInlabel() {
		return inlabel.getDisplay();
	}

	public Rainbow getInRainbow(Rainbow defaultColor) {
		return inlabel.getRainbow(defaultColor);
	}

	public Rainbow getLabelPositiveRainbow(Rainbow defaultColor) {
		return labelPositive.getRainbow(defaultColor);
	}

	public final Ftile getFtile() {
		return ftile;
	}

	public ISkinParam skinParam() {
		return ftile.skinParam();
	}

	public final HColor getColor() {
		return color;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Instruction getLast() {
		return list.getLast();
	}

	public boolean isOnlySingleStopOrSpot() {
		return list.isOnlySingleStopOrSpot();
	}

	public void setSpecial(LinkRendering link) {
		this.special = link;
	}

	public final LinkRendering getSpecial() {
		return special;
	}

}
