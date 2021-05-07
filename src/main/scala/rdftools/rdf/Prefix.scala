/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

import collection.immutable.HashMap

object Prefixes {
    var prefixDic = new HashMap[String,Iri]

    def prefix(pref:String,iri:Iri)={
        prefixDic += (pref->iri)
    }

    
}
