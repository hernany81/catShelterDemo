import org.apache.commons.lang.math.RandomUtils;
import org.catshelter.domain.Breed;
import org.catshelter.domain.Cat;
import org.catshelter.domain.Coat;
import org.springframework.util.ResourceUtils;

class BootStrap {

    def init = { servletContext ->
		
		if(Breed.getCount() == 0) {
			// Initialize breeds
			def File breedsFile = ResourceUtils.getFile("classpath:bootstrap/breeds.txt");
			breedsFile.eachLine {
				new Breed(name: it).save(validate: true, failOnError: true, flush: true);
			}
		}
		
		if(Coat.getCount() == 0) {
			// Initialize coats
			new Coat(label: 'Hairless').save(validate: true, failOnError: true, flush: true);
			new Coat(label: 'Short').save(validate: true, failOnError: true, flush: true);
			new Coat(label: 'Semi-long').save(validate: true, failOnError: true, flush: true);
			new Coat(label: 'Long').save(validate: true, failOnError: true, flush: true);
		}
		
		if(Cat.getCount() == 0) {
			def File namesFile = ResourceUtils.getFile("classpath:bootstrap/names.txt");
			def List<String> names = new ArrayList<String>();
			def List<Coat> coats = Coat.list();
			def List<Breed> breeds = Breed.list();
			
			namesFile.eachLine {  
				names.add(it);
			}
			
			50.times {
				def String name = names[RandomUtils.nextInt(names.size() - 1)];
				def Coat coat = coats[RandomUtils.nextInt(coats.size() - 1)];
				def Breed breed = breeds[RandomUtils.nextInt(breeds.size() - 1)];
				def Date arrival = new Date().minus(RandomUtils.nextInt(30));
				
				new Cat(name: name, coat: coat, breed: breed, arrivalDate: arrival).save(validate: true, failOnError: true, flush: true);  
			}
		}
    }
	
    def destroy = {
    }
}
