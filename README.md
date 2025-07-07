# OAPEN website

Oapen website uses:

- ~Spring MVC 4 https://spring.io~
- Spring Boot 2.7
- Thymeleaf https://www.thymeleaf.org/
- Rome tools https://github.com/rometools/rome
- Flexmark https://github.com/vsch/flexmark-java

And the following home brewn repositories:

- GraphQL Java client https://github.com/trilobiet/graphqlweb
- Strapi CMS API configuration for a simple website https://github.com/trilobiet/strapi-simple-website

## Frontend build

This step is only needed if you’re updating the SCSS files in `frontend/`:

```bash
npm install
npm run build
```

This will compile Bulma v1 along with our custom styles and output a single `styles.min.css` file to the correct `static-assets/css/` directory, where it will be served by the Java/Maven application.

## Version 3 2024

- Switched to Spring Boot

## Version 3.1 2025

- Moved to UpCloud.com

	UPDATE strapi36_oatoolkit.upload_file
	SET url = replace(url,"fra1.digitaloceanspaces.com","o172i.upcloudobjects.com");
	
	UPDATE strapi36_doabooksweb.upload_file
	SET url = replace(url,"ams3.digitaloceanspaces.com","o172i.upcloudobjects.com");
	
	UPDATE strapi36_oapenweb.upload_file
	SET url = replace(url,"fra1.digitaloceanspaces.com","o172i.upcloudobjects.com");
	
	UPDATE strapi36_oapenweb.articles
	SET 
		content = replace(content,"fra1.digitaloceanspaces.com","o172i.upcloudobjects.com"),
		content = replace(content,"ams3.digitaloceanspaces.com","o172i.upcloudobjects.com")
	WHERE 
		content LIKE '%.digitaloceanspaces.com%';
	
	UPDATE strapi36_doabooksweb.articles
	SET 
		content = replace(content,"fra1.digitaloceanspaces.com","o172i.upcloudobjects.com"),
	    content = replace(content,"ams3.digitaloceanspaces.com","o172i.upcloudobjects.com")
	WHERE 
		content LIKE '%.digitaloceanspaces.com%';
	    
	UPDATE strapi36_oatoolkit.articles
	SET 
		content = replace(content,"fra1.digitaloceanspaces.com","o172i.upcloudobjects.com"),
	    content = replace(content,"ams3.digitaloceanspaces.com","o172i.upcloudobjects.com")
	WHERE 
		content LIKE '%.digitaloceanspaces.com%';    
	
	