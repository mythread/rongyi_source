(function($, undefined){
	var HTML_CHARS = {
		'&' : '&amp;',
		'<' : '&lt;',
		'>' : '&gt;',
		'"' : '&quot;',
		"'" : '&#x27;',
		'/' : '&#x2F;',
		'`' : '&#x60;'
	};
	
	$.extend({
		
		isNumber: function(o){
			return $.isNumeric(o);
		},
		
		escapeHTML : function(html) {
			return html.replace(/[&<>"'\/`]/g, function(match) {
				return HTML_CHARS[match];
			});
		},
		
		isObject: function(o) {
			return (o && ( typeof o === 'object' || $.isFunction(o))) || false;
		},
		
		isString: function(o) {
			return typeof o === 'string';
		},
		
		ellipsis: function(str, limit){
			var subAry = [];
			for(var i = 0, len = 0, l = str.length; i < l; i++){
				if(str.charCodeAt(i) > 255){
					len += 2;
				}else{
					len += 1;
				}
				if(len <= limit){
					subAry.push(str.charAt(i));
				}
			}
			
			if(len > limit){
				return subAry.join('') + '...';
			}
			return str;
		},
		/**
		 * Returns a simple string representation of the object or array.
		 * Other types of objects will be returned unprocessed.  Arrays
		 * are expected to be indexed.  Use object notation for
		 * associative arrays.
		 * @method dump
		 * @since 2.3.0
		 * @param o {Object} The object to dump
		 * @param d {int} How deep to recurse child objects, default 3
		 * @return {String} the dump result
		 */
		dump: function(o, d) {
			var i, len, s = [], OBJ = "{...}", FUN = "f(){...}", COMMA = ', ', ARROW = ' => ';

			// Cast non-objects to string
			// Skip dates because the std toString is what we want
			// Skip HTMLElement-like objects because trying to dump
			// an element will cause an unhandled exception in FF 2.x
			if(!$.isObject(o)) {
				return o + "";
			} else if( o instanceof Date || ("nodeType" in o && "tagName" in o)) {
				return o;
			} else if($.isFunction(o)) {
				return FUN;
			}

			// dig into child objects the depth specifed. Default 3
			d = ($.isNumber(d)) ? d : 3;

			// arrays [1, 2, 3]
			if($.isArray(o)) {
				s.push("[");
				for( i = 0, len = o.length; i < len; i = i + 1) {
					if($.isObject(o[i])) {
						s.push((d > 0) ? $.dump(o[i], d - 1) : OBJ);
					} else {
						s.push(o[i]);
					}
					s.push(COMMA);
				}
				if(s.length > 1) {
					s.pop();
				}
				s.push("]");
				// objects {k1 => v1, k2 => v2}
			} else {
				s.push("{");
				for(i in o) {
					if(o.hasOwnProperty(i)) {
						s.push(i + ARROW);
						if($.isObject(o[i])) {
							s.push((d > 0) ? $.dump(o[i], d - 1) : OBJ);
						} else {
							s.push(o[i]);
						}
						s.push(COMMA);
					}
				}
				if(s.length > 1) {
					s.pop();
				}
				s.push("}");
			}

			return s.join("");
		},

		/**
		 * Does variable substitution on a string. It scans through the string
		 * looking for expressions enclosed in { } braces. If an expression is
		 * found, it is used a key on the object. If there is a space in the
		 * key, the first word is used for the key and the rest is provided to
		 * an optional function to be used to programatically determine the
		 * value (the extra information might be used for this decision). If the
		 * value for the key in the object, or what is returned from the
		 * function has a string value, number value, or object value, it is
		 * substituted for the bracket expression and it repeats. If this value
		 * is an object, it uses the Object's toString() if this has been
		 * overridden, otherwise it does a shallow dump of the key/value pairs.
		 * 
		 * By specifying the recurse option, the string is rescanned after every
		 * replacement, allowing for nested template substitutions. The side
		 * effect of this option is that curly braces in the replacement content
		 * must be encoded.
		 * 
		 * @method substitute
		 * @since 2.3.0
		 * @param s
		 *            {String} The string that will be modified.
		 * @param o
		 *            {Object} An object containing the replacement values
		 * @param f
		 *            {Function} An optional function that can be used to
		 *            process each match. It receives the key, value, and any
		 *            extra metadata included with the key inside of the braces.
		 * @param recurse
		 *            {boolean} default true - if not false, the replaced string
		 *            will be rescanned so that nested substitutions are
		 *            possible.
		 * @return {String} the substituted string
		 */
		substitute: function (s, o, f, recurse) {
			var i, j, k, key, v, meta, saved=[], token, lidx=s.length,
				DUMP='dump', SPACE=' ', LBRACE='{', RBRACE='}',
				dump, objstr;

			for (;;) {
				i = s.lastIndexOf(LBRACE, lidx);
				if (i < 0) {
					break;
				}
				j = s.indexOf(RBRACE, i);
				if (i + 1 > j) {
					break;
				}

				//Extract key and meta info
				token = s.substring(i + 1, j);
				key = token;
				meta = null;
				k = key.indexOf(SPACE);
				if (k > -1) {
					meta = key.substring(k + 1);
					key = key.substring(0, k);
				}

				// lookup the value
				v = o[key];

				// if a substitution function was provided, execute it
				if (f) {
					v = f(key, v, meta);
				}

				if ($.isObject(v)) {
					if ($.isArray(v)) {
						v = $.dump(v, parseInt(meta, 10));
					} else {
						meta = meta || "";

						// look for the keyword 'dump', if found force obj dump
						dump = meta.indexOf(DUMP);
						if (dump > -1) {
							meta = meta.substring(4);
						}

						objstr = v.toString();

						// use the toString if it is not the Object toString
						// and the 'dump' meta info was not found
						if (objstr === OBJECT_TOSTRING || dump > -1) {
							v = $.dump(v, parseInt(meta, 10));
						} else {
							v = objstr;
						}
					}
				} else if (!$.isString(v) && !$.isNumber(v)) {
					// This {block} has no replace string. Save it for later.
					v = "~-" + saved.length + "-~";
					saved[saved.length] = token;

					// break;
				}

				s = s.substring(0, i) + v + s.substring(j + 1);

				if (recurse === false) {
					lidx = i-1;
				}

			}

			// restore saved {block}s
			for (i=saved.length-1; i>=0; i=i-1) {
				s = s.replace(new RegExp("~-" + i + "-~"), "{"  + saved[i] + "}", "g");
			}

			return s;
		},
		
		cookie: function(name, value, options) {
			if (typeof value != 'undefined') { // name and value given, set cookie
				options = options || {};
				if (value === null) {
					value = '';
					options.expires = -1;
				}
				var expires = '';
				if (options.expires
						&& (typeof options.expires == 'number' || options.expires.toUTCString)) {
					var date;
					if (typeof options.expires == 'number') {
						date = new Date();
						date.setTime(date.getTime()
								+ (options.expires * 24 * 60 * 60 * 1000));
					} else {
						date = options.expires;
					}
					expires = '; expires=' + date.toUTCString();
					// use expires
					// attribute,
					// max-age is not
					// supported by IE
				}
				// CAUTION: Needed to parenthesize options.path and options.domain
				// in the following expressions, otherwise they evaluate to undefined
				// in the packed version for some reason...
				var path = options.path ? '; path=' + (options.path) : '';
				var domain = options.domain ? '; domain=' + (options.domain) : '';
				var secure = options.secure ? '; secure' : '';
				document.cookie = [ name, '=', encodeURIComponent(value), expires,
						path, domain, secure ].join('');
			} else { // only name given, get cookie
				var cookieValue = null;
				if (document.cookie && document.cookie != '') {
					var cookies = document.cookie.split(';');
					for ( var i = 0; i < cookies.length; i++) {
						var cookie = jQuery.trim(cookies[i]);
						// Does this cookie string begin with the name we want?
						if (cookie.substring(0, name.length + 1) == (name + '=')) {
							cookieValue = decodeURIComponent(cookie
									.substring(name.length + 1));
							break;
						}
					}
				}
				return cookieValue;
			}
		}
		
	});

})(jQuery);
