package profiling;

import java.util.*;

import model.storing.DataStorage;
import model.data.auxiliary.ColorDiscrete;
import model.data.auxiliary.ProductType;
import model.data.auxiliary.ServiceAmountTuple;
import model.data.datatypes.*;
import exceptions.*;

public class Profiler {

	private static final int iterations = 10000;
	private static final int repeat = 10;
	
	public static List<String> profileTestCase() 
		throws InvalidArgumentException, WrongConsoleInputException {
		DataStorage dataModel = new DataStorage();
		List<String> returnValue = new ArrayList<String>();
		double time = 0, begintime, endtime;
		Random numberGenerator = new Random();
		DataType usable;
		String testValues = "";
		
		System.out.println("Product addition");
		returnValue.add("Product addition time for " +
				iterations + " units:");
		String manufacturerName, modelName;
		Date releaseDate;
		for(long i = 0; i < iterations; i++) {
			manufacturerName = "IvanIvanych" + numberGenerator.nextInt(iterations);
			modelName = "IvanIvanych" + (Integer)numberGenerator.nextInt(iterations);
			releaseDate = new Date();
			releaseDate.setYear(1911 + numberGenerator.nextInt(200));
			begintime = System.currentTimeMillis();
			usable = Product.getInstance(true, 
					null, 
					manufacturerName,
					ProductType.Phone, 
					modelName,
					ColorDiscrete.Red,
					releaseDate
					);
			dataModel.add(usable);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		returnValue.add(time + "ms");
		time = 0;
		
		
		System.out.println("Client addition");
		String name, lastName;
		Date birthDate;
		for(long i = 0; i < iterations; i++) {
			name = "IvanIvanych" + numberGenerator.nextInt(iterations);
			lastName = "IvanIvanych" + numberGenerator.nextInt(iterations);
			birthDate = new Date();
			birthDate.setYear(1911 + numberGenerator.nextInt(200));
			begintime = System.currentTimeMillis();
			usable = Client.getInstance(true, name, lastName, birthDate, null);
			dataModel.add(usable);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		returnValue.add("Client addition time for " +
				iterations + " units:");
		returnValue.add(time + "ms");
		time = 0;
		
		
		System.out.println("Order addition");
		DataType client, product;
		Date dateTimeMade;
		List<ServiceAmountTuple> services;
		for(long i = 0; i < iterations; i++) {
			client = Client.getInstance(false, null, null, null,
					numberGenerator.nextInt((int)iterations));
			product = Product.getInstance(false, 
					numberGenerator.nextInt((int)iterations),
					null, null, null, null, null);
			dateTimeMade = new Date();
			services = new ArrayList<ServiceAmountTuple>();
			dateTimeMade.setYear(1911 + numberGenerator.nextInt(200));
			begintime = System.currentTimeMillis();
			services.add(new ServiceAmountTuple((Product)dataModel.
					getByID(product),1));
			usable = Order.getInstance(true, null,
					(Client)dataModel.getByID(client),
					dateTimeMade,
					services);
			dataModel.add(usable);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		returnValue.add("Order addition time for " +
				iterations + " units:");
		returnValue.add(time + "ms");
		time = 0;
		
		System.out.println("Client name search");
		for(long i = 0; i < repeat; i++) {
			name = ((Integer)numberGenerator.nextInt(iterations)).toString();
			lastName = ((Integer)numberGenerator.nextInt(iterations)).toString();
			begintime = System.currentTimeMillis();
			usable = Client.getInstance(false, name, lastName, null, null);
			dataModel.select(usable);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		time /= repeat;
		returnValue.add("Client selection by name average for " +
				repeat + " iterations:");
		returnValue.add(time + "ms");
		time = 0;
		
		System.out.println("Client birth date search");
		for(long i = 0; i < repeat; i++) {
			birthDate = new Date();
			begintime = System.currentTimeMillis();
			usable = Client.getInstance(false, null, null, birthDate, null);
			dataModel.select(usable);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		time /= repeat;
		returnValue.add("Client selection by birth date average for " +
				repeat + " iterations:");
		returnValue.add(time + "ms");
		time = 0;
		
		System.out.println("Product model name search");
		for(long i = 0; i < repeat; i++) {
			modelName = ((Integer)numberGenerator.nextInt(iterations)).toString();
			begintime = System.currentTimeMillis();
			usable = Product.getInstance(false, null, null, null, modelName, null, null);
			dataModel.select(usable);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		time /= repeat;
		returnValue.add("Product selection by model name average for " +
				repeat + " iterations:");
		returnValue.add(time + "ms");
		time = 0;
		
		System.out.println("Client name sort");
		usable = Client.getInstance(false, null,
				null, null, null);
		Comparator<Object> comparator = usable.getComparatorsMap().get("name");
		for(long i = 0; i < repeat; i++) {
			begintime = System.currentTimeMillis();
			dataModel.select(usable, comparator);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		time /= repeat;
		returnValue.add("Client full selection with sort by name average for " +
				repeat + " iterations:");
		returnValue.add(time + "ms");
		time = 0;
		
		System.out.println("Product model name sort");
		usable = Product.getInstance(false, null, null, null, null, null, null);
		comparator = usable.getComparatorsMap().get("modelName");
		for(long i = 0; i < repeat; i++) {
			begintime = System.currentTimeMillis();
			dataModel.select(usable, comparator);
			endtime = System.currentTimeMillis();
			time += endtime - begintime;
		}
		time /= repeat;
		returnValue.add("Product full selection with sort by modelName average for " +
				repeat + " iterations:");
		returnValue.add(time + "ms");
		time = 0;
		
		return returnValue;
	}
	
}
