   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ${pojo.getDeclarationName()}) ) return false;
		 ${pojo.getDeclarationName()} castOther = ( ${pojo.getDeclarationName()} ) other; 
         return this.getId() != null && castOther.getId() != null && this.getId().equals(castOther.getId());
   }