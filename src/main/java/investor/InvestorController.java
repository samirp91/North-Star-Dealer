package investor;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class InvestorController {

	private final InvestorRepository repository;

	InvestorController(InvestorRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping("/Investors")
	Iterable<Investor> all() {
		return repository.findAll();
	}

	@PostMapping("/Investors")
	Investor newInvestor(@RequestBody Investor newInvestor) {
		return repository.save(newInvestor);
	}

	// Single item

	@GetMapping("/Investors/{id}")
	Investor one(@PathVariable Long id) {

		return repository.findById(id)
			.orElseThrow(() -> new InvestorNotFoundException(id));
	}

	@PutMapping("/Investors/{id}")
	Investor replaceInvestor(@RequestBody Investor newInvestor, @PathVariable Long id) {

		return repository.findById(id)
			.map(Investor -> {
				Investor.setName(newInvestor.getName());
				Investor.setRole(newInvestor.getRole());
				return repository.save(Investor);
			})
			.orElseGet(() -> {
				newInvestor.setId(id);
				return repository.save(newInvestor);
			});
	}

	@DeleteMapping("/Investors/{id}")
	void deleteInvestor(@PathVariable Long id) {
		repository.deleteById(id);
	}
}