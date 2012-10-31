package org.claros.commons.mail.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import org.claros.commons.mail.models.EmailHeader;

public class ComparatorFrom implements Comparator {
	private int left = 1;
	private int right = -1;
	private Collator coll;

	public ComparatorFrom(boolean ascending, Locale loc) {
		if (ascending) {
			left = -1;
			right = 1;
		}
		coll = Collator.getInstance(loc);
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		if (arg0 != null && arg1 != null && arg0 instanceof EmailHeader && arg1 instanceof EmailHeader) {
			EmailHeader h1 = (EmailHeader)arg0;
			EmailHeader h2 = (EmailHeader)arg1;
			String from1 = org.claros.commons.mail.utility.Utility.addressArrToString(h1.getFrom());
			String from2 = org.claros.commons.mail.utility.Utility.addressArrToString(h2.getFrom());

			if (from1 == null) {
				return right;
			} else if (from2 == null) {
				return left;
			} else if (coll.compare(from1, from2) > 0) {
				return right;
			} else if (coll.compare(from1, from2) < 0) {
				return left;
			} else {
				return 0;
			}
		}
		return right;
	}
}
